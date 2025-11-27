package com.example.SagarBlog.Security;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Base64;

public class JwtKeyGenerator {

    public static void main(String[] args) {
        String secretKey = generateSecretKey();
        System.out.println("\n=== JWT Secret Key Generated ===");
        System.out.println("Copy this line to your application.properties:");
        System.out.println("jwt.secret=" + secretKey);
        System.out.println("================================\n");
    }

    public static String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }
}