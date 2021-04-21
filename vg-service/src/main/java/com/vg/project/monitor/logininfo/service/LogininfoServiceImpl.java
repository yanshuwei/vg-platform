package com.vg.project.monitor.logininfo.service;

import com.vg.common.support.Convert;
import com.vg.project.monitor.logininfo.domain.Logininfo;
import com.vg.project.monitor.logininfo.mapper.LogininfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class LogininfoServiceImpl implements ILogininfoService
{

    @Autowired
    private LogininfoMapper logininfoMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfo 访问日志对象
     */
    @Override
    public void insertLogininfo(Logininfo logininfo)
    {
        logininfoMapper.insertLogininfo(logininfo);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfo 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<Logininfo> selectLogininfoList(Logininfo logininfo)
    {
        return logininfoMapper.selectLogininfoList(logininfo);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininfoByIds(String ids)
    {
        return logininfoMapper.deleteLogininfoByIds(Convert.toStrArray(ids));
    }
}
