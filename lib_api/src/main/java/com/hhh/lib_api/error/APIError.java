package com.hhh.lib_api.error;


/**
 * 最终反馈给用户端网络访问错误的error对象
 */

public class APIError extends Error{
    /*错误码*/
    private int code;
    /*错误信息*/
    private ApiErrorDetailRep rep;
    /*显示的信息*/
    private String displayMessage;

    public APIError(Throwable e, int code, ApiErrorDetailRep rep) {
        super(e);
        this.displayMessage = e.getMessage() == null ? "" : e.getMessage();
        this.code = code;
        this.rep = rep;
    }

    public APIError(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public ApiErrorDetailRep getRep() {
        return rep;
    }

    public void setRep(ApiErrorDetailRep rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return displayMessage;
    }
}
