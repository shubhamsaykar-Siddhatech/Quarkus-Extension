package com.siddhatech.exceptions;

public class ErrorResponse {

    private int errorCode;
    private String developerMsg;
    private String msg;
    private long date;

    public ErrorResponse(int errorCode, String developerMsg, String msg, long date) {
        this.errorCode = errorCode;
        this.developerMsg = developerMsg;
        this.msg = msg;
        this.date = date;
    }

    public ErrorResponse(int errorCode, String developerMsg, String msg) {
        this.errorCode = errorCode;
        this.developerMsg = developerMsg;
        this.msg = msg;
    }

    public ErrorResponse() {
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

    public long getDate() {
        return System.currentTimeMillis();
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", developerMsg='" + developerMsg + '\'' +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}
