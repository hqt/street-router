package com.fpt.router.web.action.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by datnt on 11/14/2015.
 */
public class PasswordUtils {

    public String md5(String password) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md != null) {
            md.update(password.getBytes());
        }

        byte byteData[] = new byte[0];
        if (md != null) {
            byteData = md.digest();
        }

        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("Digest(in hex format):: " + hexString.toString());

        return hexString.toString();
    }

}
