package com.vg.project.monitor.logininfo.service;

import com.vg.project.monitor.logininfo.domain.Logininfo;

import java.util.List;


/**
 * 系统访问日志情况信息 服务层
 * 
 * @author ruoyi
 */
public interface ILogininfoService
{

    /**
     * 新增系统登录日志
     * 
     * @param logininfo 访问日志对象
     */
    public void insertLogininfo(Logininfo logininfo);

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfo 访问日志对象
     * @return 登录记录集合
     */
    public List<Logininfo> selectLogininfoList(Logininfo logininfo);

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    public int deleteLogininfoByIds(String ids);
}
