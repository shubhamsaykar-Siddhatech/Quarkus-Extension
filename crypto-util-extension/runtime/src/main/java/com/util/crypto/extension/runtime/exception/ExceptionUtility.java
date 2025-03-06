package com.util.crypto.extension.runtime.exception;



import org.jboss.logging.Logger;


public class ExceptionUtility {

    private static final Logger logger = Logger.getLogger(ExceptionUtility.class);



    private ExceptionUtility() {
        logger.info("ExceptionUtility");
    }

    public static CryptoExceptionMessage prepareCryptoExceptExceptionMessage(String code, String description,
                                                                       Exception parentException) {
        if (parentException != null) {
            logger.error(parentException.getMessage(),parentException);
        } else {
            logger.error(description);
        }
        CryptoExceptionType cryptoExceptionType = new CryptoExceptionType();
        cryptoExceptionType.setCode(code);
        cryptoExceptionType.setDescription(description);
        return new CryptoExceptionMessage(
                parentException != null ? parentException.getMessage() : description,
                cryptoExceptionType
        );
    }


}
