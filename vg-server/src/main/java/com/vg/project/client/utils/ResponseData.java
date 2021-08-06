package com.vg.project.client.utils;

/**
 * @Author: James
 * @Date: 2019/3/7 14:49
 * @Description:
 */
public class ResponseData {
    private String result;

    private String message;

    private Object properties;

    public ResponseData(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getProperties() {
        return properties == null ? "{}":properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }
}
