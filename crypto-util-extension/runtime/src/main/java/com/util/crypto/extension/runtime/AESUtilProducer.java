package com.util.crypto.extension.runtime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import jakarta.inject.Named;
import org.eclipse.microprofile.config.Config;

@ApplicationScoped
public class AESUtilProducer {



    @Produces
    @ApplicationScoped
    @Named("aesUtil1")
    public AESUtil createAESUtil1(Config config) {
        String secretKey = config.getValue("security.aes.secretkey1", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }

    @Produces
    @ApplicationScoped
    @Named("aesUtil2")
    public AESUtil createAESUtil2(Config config) {
        String secretKey = config.getValue("security.aes.secretkey2", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }

    @Produces
    @ApplicationScoped
    @Named("aesUtil3")
    public AESUtil createAESUtil3(Config config) {
        String secretKey = config.getValue("security.aes.secretkey3", String.class);
        AESUtil aesUtil = new AESUtil();
        aesUtil.init(secretKey);
        return aesUtil;
    }
}
