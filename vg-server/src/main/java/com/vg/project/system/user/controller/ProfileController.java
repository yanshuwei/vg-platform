package com.vg.project.system.user.controller;

import com.vg.framework.aspectj.lang.annotation.Log;
import com.vg.framework.aspectj.lang.enums.BusinessType;
import com.vg.framework.config.RuoYiConfig;
import com.vg.framework.util.FileUploadUtils;
import com.vg.framework.web.controller.BaseController;
import com.vg.framework.web.domain.AjaxResult;
import com.vg.project.monitor.service.DictService;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.system.dept.domain.Dept;
import com.vg.project.system.dept.service.IDeptService;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.service.IUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * 个人信息 业务处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;

    @Autowired
    private DictService dict;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap) {
        User user = ShiroUtils.getUser();
        user.setSex(dict.getLabel("sys_user_sex", user.getSex()));

        String deptName = "";
        if (user.getDeptList() != null && user.getDeptList().size() > 0) {
            for (Dept dept : user.getDeptList()) {
                deptName += " | " + dept.getDeptName();
            }
        }

        mmap.put("deptName", deptName.replaceFirst(" \\| ", ""));
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        User user = ShiroUtils.getUser();
        String encrypt = new Md5Hash(user.getLoginName() + password + user.getSalt()).toHex().toString();
        if (user.getPassword().equals(encrypt)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user) {
        User ur = userService.selectUserById(user.getUserId());
        user.setUserName(ur.getUserName());
        user.setEmail(ur.getEmail());
        int rows = userService.resetUserPwd(user);
        if (rows > 0) {
            setUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(User user) {
        if (userService.updateUserInfo(user) > 0) {
            setUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(User user, @RequestParam("avatarfile") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getUploads() + File.separator + "profile", file);
                user.setAvatar(avatar);
                if (userService.updateUserInfo(user) > 0) {
                    setUser(userService.selectUserById(user.getUserId()));
                    return success();
                }
            }
            return error();
        } catch (Exception e) {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }

    public void setUser(User user) {
        ShiroUtils.setUser(user);
    }

}
