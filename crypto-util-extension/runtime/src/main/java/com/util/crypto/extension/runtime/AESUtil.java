package com.util.crypto.extension.runtime;

import com.util.crypto.extension.runtime.exception.CryptoExceptionMessage;
import com.util.crypto.extension.runtime.exception.ExceptionUtility;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@ApplicationScoped
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    public AESUtil() {
    }

    private String secretKey;


    public void init(String secretKey){
        this.secretKey = secretKey;
    }

    public String encrypt(String text) throws CryptoExceptionMessage {
        return encrypt(text, secretKey);
    }

    public String decrypt(String text) throws CryptoExceptionMessage {
        return decrypt(text, secretKey);
    }


    public String encrypt(String data, String secretKeyString) throws CryptoExceptionMessage {
        try {
            SecretKey secretKey = getKeyFromConfig(secretKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Encryption failed due to missing algorithm : {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Encryption failed due to missing algorithm", null);
        } catch (NoSuchPaddingException e) {
            log.error("Encryption failed due to missing padding : {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Encryption failed due to missing padding", null);
        } catch (InvalidKeyException e) {
            log.error("Invalid encryption key provided: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Invalid encryption key provided:", null);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error during encryption: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Error during encryption:", null);
        }

    }

    public String decrypt(String data, String secretKeyString) throws CryptoExceptionMessage {
        try {
            SecretKey secretKey = getKeyFromConfig(secretKeyString);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Decryption failed due to missing algorithm : {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Decryption failed due to missing algorithm", null);
        } catch (NoSuchPaddingException e) {
            log.error("Decryption failed due to missing padding : {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Decryption failed due to missing padding", null);
        } catch (InvalidKeyException e) {
            log.error("Invalid decryption key provided: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Invalid decryption key provided:", null);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error during decryption: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Error during decryption:", null);
        }

    }

    private SecretKey getKeyFromConfig(String secretKeyString) {
        log.info(" Secret key for encryption::{}" + secretKeyString);
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }



//  code to initialise the key into memory only once at time of object injection
    /*
    private String secretAesKey;

    private static int loadCounter = 0;
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }




    @PostConstruct
    public void init(){
        loadCounter++;
        log.info(" Secret key loaded. Load count: {}", loadCounter);
        try{
            String secretKeyString = ConfigProvider.getConfig().getValue("security.aes.secretkey1", String.class);
            this.secretKey = secretAesKey;
            log.info("Secret key loaded successfully.");
        }catch (Exception e){
            log.error("Failed to load secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to load encryption key.", e);
        }
    }

    private String getSecretKey() {
        return ConfigProvider.getConfig().getValue("security.aes.secretkey", String.class);
    }


     */
}
