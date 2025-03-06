package com.siddhatech.AESUtil;

import java.security.*;
import java.util.Base64;

public class RSAUtil {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("Public Key:\n" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("Private Key:\n" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }
}
