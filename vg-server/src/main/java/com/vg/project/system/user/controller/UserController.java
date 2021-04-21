package com.vg.project.system.user.controller;

import com.vg.common.utils.StringUtils;
import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.framework.web.page.TableDataInfo;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.system.post.service.IPostService;
import com.vg.project.system.role.domain.Role;
import com.vg.project.system.role.service.IRoleService;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.service.IUserRoleService;
import com.vg.project.system.user.service.IUserService;
import com.vg.project.util.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    private String prefix = "system/user";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;
    @Autowired
    private IUserRoleService userRoleService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        User user = ShiroUtils.getUser();
        String roleType = user.getUserType();
        if (roleType.equals("Platform")) {
            return prefix + "/platform/index";
        }
        return prefix + "/other/index";
//        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user) {
        User loginUser = ShiroUtils.getUser();
        Long loginUserId = loginUser.getUserId();
        String roleType = loginUser.getUserType();
        Role role = new Role();
        role.setUserId(loginUserId);

        if (roleType.equals("Platform")) {
            startPage();
            List<User> list = userService.selectUserList(user);
            return getDataTable(list);
        }
        if (roleType.equals("Operation")) {
            List<Role> roleList = roleService.selectOperationRoleList(role);//查找所有属于某运营商创建的所有角色
            Long[] roleIds = new Long[roleList.size()];
            for (int i = 0; i < roleList.size(); i++) {
                roleIds[i] = roleList.get(i).getRoleId();
            }
            Long[] userIds = userRoleService.selectUserIdsByRoleIds(roleIds);//根据角色id查找某运营商创建的所有用户id
            user.setUserIds(userIds);
            startPage();
            List<User> list = userService.selectUserList(user);
            return getDataTable(list);
        }
        List<Role> roleList = roleService.selectSupplierRoleList(role);
        Long[] roleIds = new Long[roleList.size()];
        for (int i = 0; i < roleList.size(); i++) {
            roleIds[i] = roleList.get(i).getRoleId();
        }
        Long[] userIds = userRoleService.selectUserIdsByRoleIds(roleIds);
        user.setUserIds(userIds);
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
       /* startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);*/
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(User user) throws Exception {
        try {
            List<User> list = userService.selectUserList(user);
            ExcelUtil<User> util = new ExcelUtil<User>(User.class);
            return util.exportExcel(list, "user");
        } catch (Exception e) {
            return error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        User user = ShiroUtils.getUser();
        String roleType = user.getUserType();
        Role role = new Role();
        /*mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());*/
        if (roleType.equals("Platform")) {
            role.setCreateType(1);
            List<Role> roleList = roleService.selectRoleList(role);
            mmap.put("roles", roleList);
            mmap.put("posts", postService.selectPostAll());
            return prefix + "/platform/add";
        }
        role.setUserId(user.getUserId());
        if (roleType.equals("Operation")) {
            List<Role> roleList = roleService.selectOperationRoleList(role);
            mmap.put("roles", roleList);
            return prefix + "/other/add";
        }
        List<Role> roleList = roleService.selectSupplierRoleList(role);
        mmap.put("roles", roleList);
        return prefix + "/other/add";

//        return prefix + "/add";
    }

   /* //获取相应级别角色
    @RequestMapping("/selectRoles")
    @ResponseBody
    public ResponseData selectRoles(@RequestParam("roleType") String roleType)
    {
        ResponseData response;
        Role role=new Role();
        role.setRoleType(roleType);
        role.setCreateType(1);
        List<Role> roleList=roleService.selectRoleList(role);
        response=new ResponseData("1", "角色获取成功！");
        response.setProperties(ImmutableMap.of("roleList",roleList));
        return response;
    }*/

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(User user) {
        User loginUser = ShiroUtils.getUser();
        String roleType = loginUser.getUserType();
        if (loginUser.getUserType().equals("Platform")) {
            if (!user.getUserType().equals("Platform")) {
                user.setIsDefault((long) 1);
            }
        }
        if (loginUser.getUserType().equals("Operation") || loginUser.getUserType().equals("Supplier")) {
            //user.setDeptId(loginUser.getDeptId());
            if (loginUser.getDeptList() != null) {
                Long deptIds[] = new Long[loginUser.getDeptList().size()];
                for (int i = 0; i < loginUser.getDeptList().size(); i++) {
                    deptIds[i] = loginUser.getDeptList().get(i).getDeptId();
                }
                user.setDeptIds(deptIds);
            }
            user.setUserType(roleType);
            user.setIsDefault((long) 0);
        }
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        User loginUser = ShiroUtils.getUser();
        String loginRoleType = loginUser.getUserType();

        if (loginRoleType.equals("Platform")) {
            mmap.put("user", userService.selectUserById(userId));
            mmap.put("roles", roleService.selectCheckedRolesByUserId(userId));
            mmap.put("posts", postService.selectPostsByUserId(userId));
            return prefix + "/platform/edit";
        }
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectCheckedRolesByUserId(userId));
        return prefix + "/other/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }

        User loginUser = ShiroUtils.getUser();
        if (loginUser.getUserType().equals("Operation") || loginUser.getUserType().equals("Supplier")) {
            if (loginUser.getDeptList() != null) {
                Long deptIds[] = new Long[loginUser.getDeptList().size()];
                for (int i = 0; i < loginUser.getDeptList().size(); i++) {
                    deptIds[i] = loginUser.getDeptList().get(i).getDeptId();
                }
                user.setDeptIds(deptIds);
            }
        }

        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user) {
        User ur = userService.selectUserById(user.getUserId());
        user.setUserName(ur.getUserName());
        user.setEmail(ur.getEmail());
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user) {
        return userService.checkEmailUnique(user);
    }
}