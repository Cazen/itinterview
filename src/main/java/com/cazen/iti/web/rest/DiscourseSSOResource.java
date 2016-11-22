package com.cazen.iti.web.rest;

import com.cazen.iti.domain.User;
import com.cazen.iti.service.UserService;
import com.codahale.metrics.annotation.Timed;
import org.postgresql.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * REST controller for managing Discourse(forum) SSO.
 */
@RestController
@RequestMapping("/discourse")
public class DiscourseSSOResource {

    private final Logger log = LoggerFactory.getLogger(DiscourseSSOResource.class);

    @Inject
    private UserService userService;

    /**
     * GET  /sso : Return SSO Information.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @return ModelAndView with SSO information
     * @throws Exception fucking exception
     */
    @GetMapping("/sso")
    @Timed
    protected ModelAndView returnSSOInformation(HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        log.debug("REST request getting SSO Information : {}", request);

        String secretKey = "cazen_discourse_SSO_ScretKey!@#";
        String discourseURL = "https://discourse.itinterview.co.kr";
        if (secretKey == null || secretKey.isEmpty() || discourseURL == null || discourseURL.isEmpty()) {
            return null;
        }
        String discourseSSOLoginURL = discourseURL + "/session/sso_login";

        String base64EncodedSSOParam = request.getParameter("sso");   // "sso" contains Base64-encoded query string, e.g. "nonce=ABCD"
        String base64DecodedSSOParam = new String(Base64.decode(base64EncodedSSOParam), "UTF-8");
        if (!base64DecodedSSOParam.startsWith("nonce=")) {
            return null;
        }
        String nonce = base64DecodedSSOParam.substring(6);

        // check validity of message using the agreed secret key
        String algorithm = "HmacSHA256";
        String hMACSHA256Message = hmacDigest(base64EncodedSSOParam, secretKey, algorithm);
        String sigParam = request.getParameter("sig");   // "sig" contains HMAC-signed (payload,secret key)
        if (!hMACSHA256Message.equals(sigParam)) {
            return null;  // signature is not valid. It was not signed with the agreed-upon secret key.
        }

        // At this point, the signature is valid. Now, we redirect back to Discourse with info that it needs about the logged-in user.

        // create a new payload with nonce, external_id=WISE_USER_ID, username=WISE_USERNAME, name=First name + Last name, email=WISE_USER_EMAIL)
        User signedInUser = userService.getUserWithAuthorities();
        String externalId = URLEncoder.encode(signedInUser.getId().toString(), "UTF-8");
        String username = URLEncoder.encode(signedInUser.getEmail(), "UTF-8");
        String name = URLEncoder.encode(signedInUser.getFirstName() + " " + signedInUser.getLastName(), "UTF-8");
        String email = URLEncoder.encode(signedInUser.getEmail(), "UTF-8");
        String payLoadString = "nonce="+nonce+"&name="+name+"&username="+username+"&email="+email+"&external_id="+externalId;

        String payLoadStringBase64Encoded = Base64.encodeBytes(payLoadString.getBytes()); // base64-encode the payload.
        String payLoadStringBase64EncodedURLEncoded = URLEncoder.encode(payLoadStringBase64Encoded, "UTF-8");  // url-encode it to send over http(s).
        String payLoadStringBase64EncodedHMACSHA256Signed = hmacDigest(payLoadStringBase64Encoded, secretKey, algorithm);  // sign the base64-encoded payload
        discourseSSOLoginURL += "?sso="+payLoadStringBase64EncodedURLEncoded + "&sig="+payLoadStringBase64EncodedHMACSHA256Signed;  // append params to redirect URL

        RedirectView redirectView = new RedirectView(discourseSSOLoginURL);
        return new ModelAndView(redirectView);
    }

    /**
     * Given message, key, and algorithm, returns Hashed Message Authentication Code (HMAC) digest
     *
     * @param msg
     * @param secretKey
     * @param algorithm
     * @return
     */
    public static String hmacDigest(String msg, String secretKey, String algorithm) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((secretKey).getBytes("UTF-8"), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }
}
