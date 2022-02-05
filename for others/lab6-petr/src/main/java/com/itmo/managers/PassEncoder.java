package com.itmo.managers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * хеширование пароля с перцем
 */

public class PassEncoder {
    private String pepper;

    public String getHash(String pass) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] data = messageDigest.digest((pass + pepper).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : data) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PassEncoder(String pepper) {
        this.pepper = pepper;
    }

    public String getPepper() {
        return pepper;
    }
}
