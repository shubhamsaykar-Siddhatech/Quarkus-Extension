package org.siddhatech.utility.utilClasses;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import java.util.Base64;

@ApplicationScoped
public class AESUtil {


    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public String encrypt(String data,String secretKeyString) throws Exception {
        SecretKey secretKey = getKeyFromConfig(secretKeyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String data,String secretKeyString) throws Exception {
        SecretKey secretKey = getKeyFromConfig(secretKeyString);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decryptedBytes);
    }

    private SecretKey getKeyFromConfig(String secretKeyString) {
        log.info(" Secret key for encryption::{}"+secretKeyString);
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

}
