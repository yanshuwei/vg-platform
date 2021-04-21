package com.vg.project.system.role.controller;

import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.framework.web.page.TableDataInfo;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.system.email.service.ISendEmailService;
import com.vg.project.system.role.domain.Role;
import com.vg.project.system.role.domain.RoleOrganization;
import com.vg.project.system.role.service.IRoleOrganizationService;
import com.vg.project.system.role.service.IRoleService;
import com.vg.project.system.user.domain.User;
import com.vg.project.util.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController
{

    private String prefix = "system/role";

    @Autowired
    private IRoleService roleService;
    @Autowired
	IRoleOrganizationService roleOrganizationService;
    @Autowired
	ISendEmailService sendEmailService;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role()
    {
        User user= ShiroUtils.getUser();
        String roleType = user.getUserType();
        if(roleType.equals("Platform")){
            return prefix + "/platform/index";
        }
        return prefix +  "/other/index";
        /*return prefix + "/role";*/
    }

    @RequiresPermissions("system:role:platform:view")
    @GetMapping("/platform")
    public String platformRole(){return prefix + "/platform/index";}

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Role role)
    {
        User user= ShiroUtils.getUser();
        Long userId=user.getUserId();
        String roleType = user.getUserType();
        role.setUserId(userId);
        startPage();
        if(roleType.equals("Platform")){
            role.setCreateType(1);
            List<Role> list = roleService.selectRoleList(role);
            return getDataTable(list);
        }
        if(roleType.equals("Operation")){
            List<Role> list=roleService.selectOperationRoleList(role);
            return getDataTable(list);
        }
        List<Role> list = roleService.selectSupplierRoleList(role);
        return getDataTable(list);
    }

    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Role role) throws Exception
    {
        try
        {
            List<Role> list = roleService.selectRoleList(role);
            ExcelUtil<Role> util = new ExcelUtil<Role>(Role.class);
            return util.exportExcel(list, "role");
        }
        catch (Exception e)
        {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        User user= ShiroUtils.getUser();
        String roleType = user.getUserType();
        if (roleType.equals("Platform")){
            return prefix+"/platform/add";
        }
//        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/other/add";
    }

    /**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Role role)
    {
        User user= ShiroUtils.getUser();
        Long userId=user.getUserId();
        RoleOrganization roleOrganization=roleOrganizationService.selectByUserId(userId);
        String roleType = user.getUserType();//登录者类型级别
        if(roleType.equals("Platform")){
            role.setCreateType(1);
            return toAjax(roleService.insertRole(role));
        }
        role.setCreateType(0);
        if(roleType.equals("Operation")){
            role.setRoleType("Operation");
            role.setOperatorId(roleOrganization.getOperatorId());
            return toAjax(roleService.insertRole(role));
        }
        role.setRoleType("Supplier");
        role.setSupplierCode(roleOrganization.getSupplierCode());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap)
    {
        User user= ShiroUtils.getUser();
        Long userId=user.getUserId();
        String roleType = user.getUserType();//登录者类型级别
        if (roleType.equals("Platform")){
            Role role = roleService.selectRoleById(roleId);
            role.setSupplierCode(roleOrganizationService.selectSupplierCodeByRoleId(roleId));
            mmap.put("role",role);
            if(!role.getRoleType().equals("Supplier")) {
                mmap.put("operators", null);
            }
            return prefix + "/platform/edit";
        }
        mmap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/other/edit";
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(Role role)
    {
        return toAjax(roleService.updateRole(role));
    }

    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(roleService.deleteRoleByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(Role role)
    {
        return roleService.checkRoleNameUnique(role);
    }
    
    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(Role role)
    {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree()
    {
        return prefix + "/tree";
    }

}