package com.hhh.lib_api.error;


import com.hhh.lib_core.model.WeCommonRep;

/**
 * Created by nova on 2017/10/25.
 */

public class APIError extends Error{
    /*错误码*/
    private int code;
    /*错误信息*/
    private WeCommonRep rep;
    /*显示的信息*/
    private String displayMessage;

    public APIError(Throwable e, int code, WeCommonRep rep) {
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

    public WeCommonRep getRep() {
        return rep;
    }

    public void setRep(WeCommonRep rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return displayMessage;
    }
}
