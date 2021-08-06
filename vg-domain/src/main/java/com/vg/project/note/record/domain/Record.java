package com.vg.project.note.record.domain;

import com.vg.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 记账记录表 t_record
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
public class Record extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 备注 */
	private String remark;
	/** 金额 */
	private Double money;
	/** 日期 */
	private String date;
	/** 星期 */
	private String week;
	/** 子类型 */
	private Integer kind;
	/** 性质0支出1收入 */
	private Integer quality;
	/** 记账人 */
	private Long userId;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	private String icon;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Record{" +
				"id=" + id +
				", remark=" + remark +
				", money=" + money +
				", date='" + date + '\'' +
				", week='" + week + '\'' +
				", quality=" + quality +
				", kind=" + kind +
				", userId=" + userId +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
