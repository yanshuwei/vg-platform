package com.vg.project.monitor.map.domain;

import com.vg.framework.aspectj.lang.annotation.Excel;
import com.vg.framework.web.domain.BaseEntity;

/**
 * @author sqp
 * @date 2019/3/13 16:12
 */
public class Maps extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;
    /** 项目状态 0已开班 1未开班 */
    @Excel(name = "项目状态")
    private String value;
    //经纬度，一定要在加载的js地图显示的区域内
    /** 经度 */
    @Excel(name = "经度")
    private Double longitude;
    /** 纬度 */
    @Excel(name = "纬度")
    private Double latitude;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Maps{" +
                "projectName='" + projectName + '\'' +
                ", value='" + value + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public Maps(String projectName, String value, Double longitude, Double latitude) {
        this.projectName = projectName;
        this.value = value;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Maps() { }
}
