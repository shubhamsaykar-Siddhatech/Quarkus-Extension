package com.siddhatech.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;



public class GlobalException extends WebApplicationException
{
    private int errorCode;
    private String developerMsg;
    private String msg;


    public GlobalException(int errorCode, String developerMsg, String msg){
        super(Response.status(errorCode)
                .entity(new ErrorResponse(errorCode, developerMsg,msg))
                .build());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDeveloperMsg() {
        return developerMsg;
    }

    public void setDeveloperMsg(String developerMsg) {
        this.developerMsg = developerMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
