package com.solid.principle.ocp.example2.good;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA2PasswordHasher implements PasswordHasher{
    @Override
    public MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA_256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
