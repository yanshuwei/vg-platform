package com.vg.project.system.app.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class Version  {

    //数据id
    private int id;
    //应用名称
    private String name;
    //应用id
    private String appid;
    //版本号
    private String version;
    //版本特征
    private String features;
    //更新下载地址
    private String url;
    //更新日期
    private Date createtime;

    private MultipartFile blFile;
    public Version(){}

    public MultipartFile getBlFile() {
        return blFile;
    }

    public void setBlFile(MultipartFile blFile) {
        this.blFile = blFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


}
