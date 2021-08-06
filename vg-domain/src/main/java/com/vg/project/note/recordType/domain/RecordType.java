package com.vg.project.note.recordType.domain;

import com.vg.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 记账类型表 t_record_type
 * 
 * @author ruoyi
 * @date 2021-07-29
 */
public class RecordType extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 类型名称 */
	private String name;
	/** 子类型 */
	private Integer kind;
	/** 0支出1收入 */
	private Integer quality;
	/** 用户ID */
	private Long userId;
	/** 显示顺序 */
	private Integer orderby;
	/** 图标 */
	private String icon;
	/** 创建日期 */
	private Date createTime;
	/** 更新日期 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
}
