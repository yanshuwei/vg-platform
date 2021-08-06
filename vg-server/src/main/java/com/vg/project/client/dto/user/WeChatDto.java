package com.vg.project.client.dto.user;

/**
 * @Author: James
 * @Date: 2019/4/29 10:48
 * @Description:
 */
public class WeChatDto {
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 用户登录密码
     */
    private String password;
    /**
     * 微信开放平台ID
     */
    //private String wx_openid;
    private String wx_code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getWx_openid() {
//        return wx_openid;
//    }
//
//    public void setWx_openid(String wx_openid) {
//        this.wx_openid = wx_openid;
//    }

    public String getWx_code() {
        return wx_code;
    }

    public void setWx_code(String wx_code) {
        this.wx_code = wx_code;
    }
}
