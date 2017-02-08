package com.cazen.iti.web.rest;

import com.cazen.iti.domain.User;
import com.cazen.iti.security.SecurityUtils;
import com.cazen.iti.service.UserService;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    @Inject
    private SecurityUtils securityUtils;
    /**
     * GET  /sso : Return SSO Information.
     *
     * @param payload  the HttpServletRequest
     * @param sig the HttpServletResponse
     * @return ModelAndView with SSO information
     * @throws Exception fucking exception
     */
    @GetMapping("/sso")
    @Timed
    public ResponseEntity<Void> returnSSOInformation(@RequestParam(value = "sso") String payload, @RequestParam(value = "sig") String sig) throws Exception {

        String secretKey = "cazen_discourse_SSO_ScretKey!@#";
        String discourseURL = "http://discourse.itinterview.co.kr";
        if (secretKey == null || secretKey.isEmpty() || discourseURL == null || discourseURL.isEmpty()) {
            return null;
        }
        String discourseSSOLoginURL = discourseURL + "/session/sso_login?sso=";

        if (payload == null || sig == null) {
            return ResponseEntity.ok().header("Location", "http://itinterview.co.kr/#/register").build();
        }
        if (!checksum(secretKey, payload).equals(sig)) {
            return ResponseEntity.ok().header("Location", "http://itinterview.co.kr/#/register").build();
        }
        String urlDecode = URLDecoder.decode(payload, "UTF-8");
        String nonce = new String(Base64.decodeBase64(urlDecode));

        log.error("Cazen SecurityUtils.getCurrentUserLogin() in discourse = " + SecurityUtils.getCurrentUserLogin());

        User signedInUser;
        if(SecurityUtils.isAuthenticated()) {
            signedInUser = userService.getUserWithAuthorities();
        } else {
            return ResponseEntity.ok().header("Location", "http://itinterview.co.kr/#/register").build();
        }

        log.error("SSO Called with signedInUser: " + signedInUser.toString());
        String urlEncode = nonce
            + "&name=" + URLEncoder.encode(signedInUser.getFirstName(), "UTF-8")
            + "&username=" + URLEncoder.encode(signedInUser.getFirstName(), "UTF-8")
            + "&email=" + URLEncoder.encode(signedInUser.getEmail(), "UTF-8")
            + "&external_id=" + URLEncoder.encode(signedInUser.getId() + "", "UTF-8");
        String urlBase64 = new String(Base64.encodeBase64(urlEncode.getBytes("UTF-8")));
        int length = 0;
        int maxLength = urlBase64.length();
        final int STEP = 60;
        String urlBase64Encode = "";
        while (length < maxLength) {
            urlBase64Encode += urlBase64.substring(length, length + STEP < maxLength ? length + STEP : maxLength) + "\n";
            length += STEP;
        }
        //RedirectView redirectView = new RedirectView(discourseSSOLoginURL + URLEncoder.encode(urlBase64Encode, "UTF-8") + "&sig=" +  checksum(secretKey, urlBase64Encode));
        return ResponseEntity.ok().header("Location", discourseSSOLoginURL + URLEncoder.encode(urlBase64Encode, "UTF-8") + "&sig=" +  checksum(secretKey, urlBase64Encode)).build();
        //return ResponseEntity.created(new URI("http://itinterview.co.kr/#/register")).build();
        //return redirectView;
    }


    private String checksum(String macKey, String macData) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = macKey.getBytes("UTF-8");
        byte[] dataBytes = macData.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(secretKey);
        byte[] doFinal = mac.doFinal(dataBytes);
        byte[] hexBytes = new Hex().encode(doFinal);
        return new String(hexBytes);
    }

}
