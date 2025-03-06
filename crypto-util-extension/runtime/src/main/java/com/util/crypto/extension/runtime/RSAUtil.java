package com.util.crypto.extension.runtime;

import com.util.crypto.extension.runtime.exception.CryptoExceptionMessage;
import com.util.crypto.extension.runtime.exception.ExceptionUtility;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ApplicationScoped
public class RSAUtil {

    private static final String RSA = "RSA";
    private static final Logger log = LoggerFactory.getLogger(RSAUtil.class);
    private static int loadCounter = 0;
/*
    private String getRSAPublicKey() {
        return ConfigProvider.getConfig().getValue("security.rsa.publicKey", String.class);
    }

    private String getRSAPrivateKey() {
        return ConfigProvider.getConfig().getValue("security.rsa.privateKey", String.class);
    }

 */

    private static String rsaPublicKey;
    private static String rsaPrivateKey;

    @PostConstruct
    public void init(){
        loadCounter++;
        log.info("Secret key loaded. Load count: {}", loadCounter);
        try{
            this.rsaPublicKey = ConfigProvider.getConfig().getValue("security.rsa.publicKey", String.class);
            this.rsaPrivateKey = ConfigProvider.getConfig().getValue("security.rsa.privateKey", String.class);

            log.info("Secret key loaded successfully.");
        }catch (Exception e){
            log.error("Failed to load secret key: {}", e.getMessage());
            throw new RuntimeException("Failed to load encryption key.", e);
        }
    }

    public String encrypt(String data) throws CryptoExceptionMessage {
        return encrypt(data, rsaPublicKey);
    }

    public String decrypt(String data) throws CryptoExceptionMessage {
        return decrypt(data, rsaPrivateKey);
    }


    public String encrypt(String data, String publicKeyString) throws CryptoExceptionMessage {
        try {
            PublicKey publicKey = getPublicKey(publicKeyString);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
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
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
            log.error("Error during encryption: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Error during encryption:", null);
        }

    }

    public String decrypt(String data, String privateKeyString) throws CryptoExceptionMessage {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyString);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
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
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
            log.error("Error during decryption: {}", e.getMessage());
            throw ExceptionUtility.prepareCryptoExceptExceptionMessage("500", "Error during decryption:", null);
        }
    }

    private PublicKey getPublicKey(String publicKeyString) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    private PrivateKey getPrivateKey(String privateKeyString) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }
}
