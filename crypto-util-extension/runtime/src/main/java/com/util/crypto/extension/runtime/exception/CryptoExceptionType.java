package com.util.crypto.extension.runtime.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class CryptoExceptionType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public CryptoExceptionType() {

    }
}
