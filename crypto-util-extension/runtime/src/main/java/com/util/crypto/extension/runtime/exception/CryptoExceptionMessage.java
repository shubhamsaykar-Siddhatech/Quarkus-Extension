package com.util.crypto.extension.runtime.exception;

public class CryptoExceptionMessage extends Exception {

    private static final long serialVersionUID = 1L;

    private final CryptoExceptionType faultInfo;

    public CryptoExceptionMessage(String message, CryptoExceptionType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public CryptoExceptionType getFaultInfo() {
        return faultInfo;
    }
}
