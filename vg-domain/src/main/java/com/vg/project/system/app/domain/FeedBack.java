package com.vg.project.system.app.domain;

import com.vg.framework.web.domain.BaseEntity;

import java.util.Date;

public class FeedBack extends BaseEntity {
    /*
    * 自增主键	ID	BIGINT
用户反馈	FEEDBACK	VARCHAR
用户ID	USERID	BIGINT
应用ID	APPID	VARCHAR
用户评分	SCORE	FLOAT
创建时间	CREATETIME

    * */
    private int id;
    //用户反馈内容
    private String feedback;
    //用户ID
    private String userid;
    //应用ID
    private String appid;
    //用户评分
    private float score;
    //创建时间
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
