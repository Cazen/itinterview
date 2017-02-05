package com.cazen.iti.service.util;

import com.cazen.iti.domain.SubmitTryQuestionForUser;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for caculating ELO points
 */
@Service
public class EloRatingUtil {

    public int calcELO(int myElo, int opponentsElo, boolean trueMeansWin) {
        double kFactor = 15.0;
        double expectedScore = getExpectRating(myElo, opponentsElo);
        if (trueMeansWin) {
            return myElo + (int) (kFactor * (1 - expectedScore));
        }

        return myElo + (int) (kFactor * (-expectedScore));
    }

    public double calcELOWinPercentage(int myElo, int opponentsElo) {
        return Math.pow(10, (double)myElo/400)/(Math.pow(10, (double)myElo /400) + Math.pow(10, (double)opponentsElo /400));
    }

    private double getExpectRating(int myElo, int opponentsElo) {
        return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentsElo - myElo) / 400.0)));
    }

}
