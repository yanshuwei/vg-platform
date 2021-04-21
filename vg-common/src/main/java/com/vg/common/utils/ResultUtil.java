package com.vg.common.utils;

/**
 * 消息提示
 * @author sqp
 * @date 2018/9/13 16:17
 */
public class ResultUtil
{
    private String result;
    private String message;

    public ResultUtil() {
    }

    public ResultUtil(String result, String message) {
        super();
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
}
