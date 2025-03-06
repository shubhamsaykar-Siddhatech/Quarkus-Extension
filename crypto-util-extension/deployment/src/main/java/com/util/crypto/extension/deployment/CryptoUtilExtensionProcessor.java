package com.util.crypto.extension.deployment;


import com.util.crypto.extension.runtime.AESUtil;
import com.util.crypto.extension.runtime.AESUtilProducer;
import com.util.crypto.extension.runtime.RSAUtil;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class CryptoUtilExtensionProcessor {

    private static final String FEATURE = "crypto-util-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }


    @BuildStep
    AdditionalBeanBuildItem registerBeans() {
        return AdditionalBeanBuildItem.builder()
                .addBeanClass(RSAUtil.class)
                .addBeanClass(AESUtil.class)
                .addBeanClass(AESUtilProducer.class)
                .build();
    }

}
