package com.example.tfgpruebita.ui.LoginRegister;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptar {
    public static String hashString(String stringToHash) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(stringToHash.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                stringBuffer.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
