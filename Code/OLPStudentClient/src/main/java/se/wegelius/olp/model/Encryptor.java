/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.olp.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.slf4j.LoggerFactory;

public class Encryptor {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Encryptor.class);
    private static final String SALT = "oiejHf0å9qW3";

    public static boolean authenticate(String attemptedPassword, String encryptedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        String encryptedAttemptedPassword = get_SHA_512_SecurePassword(attemptedPassword);

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored string
        return encryptedPassword.equals(encryptedAttemptedPassword);
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash) throws UnsupportedEncodingException {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SALT.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return generatedPassword;
    }

}
