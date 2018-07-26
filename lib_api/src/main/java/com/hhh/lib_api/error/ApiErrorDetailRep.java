package com.hhh.lib_api.error;

import java.util.List;

/**
 *  错误信息的具体封装
 */

public class ApiErrorDetailRep {

    private String timestamp;
    private int error;
    private String message;
    private String path;
    private String exception;
    private List<String> errors;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public void setError(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
