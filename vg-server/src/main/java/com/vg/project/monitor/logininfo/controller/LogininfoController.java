package com.vg.project.monitor.logininfo.controller;

import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.project.util.ExcelUtil;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.framework.web.page.TableDataInfo;
import com.vg.project.monitor.logininfo.domain.Logininfo;
import com.vg.project.monitor.logininfo.service.ILogininfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 系统访问记录
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/logininfo")
public class LogininfoController extends BaseController
{
    private String prefix = "monitor/logininfo";

    @Autowired
    private ILogininfoService logininfoService;

    @RequiresPermissions("monitor:logininfo:view")
    @GetMapping()
    public String logininfo()
    {
        return prefix + "/logininfo";
    }

    @RequiresPermissions("monitor:logininfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Logininfo logininfo)
    {
        startPage();
        List<Logininfo> list = logininfoService.selectLogininfoList(logininfo);
        return getDataTable(list);
    }

    @Log(title = "登陆日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:logininfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Logininfo logininfo) throws Exception
    {
        try
        {
            List<Logininfo> list = logininfoService.selectLogininfoList(logininfo);
            ExcelUtil<Logininfo> util = new ExcelUtil<Logininfo>(Logininfo.class);
            return util.exportExcel(list, "logininfo");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("monitor:logininfo:remove")
    @Log(title = "登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(logininfoService.deleteLogininfoByIds(ids));
    }
}
