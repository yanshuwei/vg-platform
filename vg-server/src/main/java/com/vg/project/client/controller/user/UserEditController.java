package com.vg.project.client.controller.user;

import com.vg.common.constant.UserConstants;
import com.vg.common.utils.StringUtils;
import com.vg.framework.config.RuoYiConfig;
import com.vg.framework.util.FileUploadUtils;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.dto.user.UserDto;
import com.vg.project.client.utils.ResponseData;
import com.vg.project.shiro.service.PasswordService;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.service.IUserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @Author: James
 * @Date: 2019/4/11 11:26
 * @Description:
 */
@Controller
public class UserEditController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordService passwordService;

    @Value("${ruoyi.uploads}")
    private String uploads;

    /**
     * 用户密码修改
     *
     * @return
     */
    @RequestDecode
    @ResponseEncode
    @ResponseBody
    @RequestMapping(value = "/client/api/edit_passwd", produces = "application/json;charset=UTF-8")
    public ResponseData editPassword(@RequestBody UserDto userDto) {
        String userId = request.getHeader("userId");

        if (StringUtils.isNotEmpty(userDto.getPassword())) {
            try {
                User user = userService.selectUserById(Long.parseLong(userId));

                if (user.getPassword().equals(passwordService.encryptPassword(user.getLoginName(), userDto.getPassword(), user.getSalt()))) {
                    if (Pattern.matches("^[a-zA-Z0-9]{8,16}$", userDto.getNewpassword())) {
                        // 保存新密码
                        user.setPassword(passwordService.encryptPassword(user.getLoginName(), userDto.getNewpassword(), user.getSalt()));
                        userService.updateUserInfo(user);
                        return new ResponseData("10000", "密码修改成功！");
                    } else {
                        return new ResponseData("100002", "密码格式不正确");
                    }
                } else {
                    return new ResponseData("100001", "原密码输入不正确！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseData("100003", "密码修改失败！");
            }
        } else {
            return new ResponseData("100001", "原密码不能为空！");
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestDecode
    @ResponseEncode
    @ResponseBody
    @RequestMapping(value = "/client/api/userinfo", produces = "application/json;charset=UTF-8")
    public ResponseData getUserInfo() {
        String userId = request.getHeader("userId");
        ResponseData result;

        try {
            User user = userService.selectUserById(Long.parseLong(userId));
            result = new ResponseData("10000", "获取用户信息成功！");
            result.setProperties(ImmutableMap.of("userName", user.getUserName(), "email", user.getEmail(), "phone", user.getPhonenumber(), "sex", user.getSex(), "avatar", "/uploads/profile/" + user.getAvatar()));
        } catch (Exception e) {
            result = new ResponseData("100001", "获取用户信息失败！");
        }
        return result;
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @RequestDecode
    @ResponseEncode
    @ResponseBody
    @RequestMapping(value = "/client/api/edit_userinfo", produces = "application/json;charset=UTF-8")
    public ResponseData editUserInfo(@RequestBody UserDto dto) {
        String userId = request.getHeader("userId");
        try {
            User user = userService.selectUserById(Long.parseLong(userId));
            user.setEmail(dto.getEmail());
            user.setPhonenumber(dto.getPhone());

            // 校验email、phone是否唯一
            if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
                return new ResponseData("100002", "邮箱已绑定其他账号！");
            } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
                return new ResponseData("100003", "手机号码已绑定其他账号！");
            }

            user.setUserName(dto.getUserName());
            user.setSex(dto.getSex());

            userService.updateUserInfo(user);
        } catch (Exception e) {
            return new ResponseData("100001", "用户信息修改失败！");
        }
        return new ResponseData("10000", "用户信息修改成功！");
    }

    /**
     * 修改用户头像
     *
     * @return
     */
    @ResponseEncode
    @ResponseBody
    @RequestMapping(value = "/client/api/edit_avatar")
    public ResponseData editAvatar(@RequestParam("avatar") MultipartFile avatar) {
        if (avatar.isEmpty()) {
            return new ResponseData("100002", "用户头像未选择！");
        }

        String fileName = avatar.getOriginalFilename().toLowerCase();
        if (fileName.indexOf(".jpg") == -1 && fileName.indexOf(".png") == -1) {
            return new ResponseData("100003", "用户头像格式或尺寸不正确！");
        }

        String url;
        String userId = request.getHeader("userId");
        try {
            url = FileUploadUtils.upload(RuoYiConfig.getUploads() + File.separator + "profile", avatar);

            User user = userService.selectUserById(Long.parseLong(userId));
            user.setAvatar(url);

            userService.updateUserInfo(user);
        } catch (Exception e) {
            return new ResponseData("100001", "头像修改失败！");
        }

        ResponseData result = new ResponseData("10000", "用户头像修改成功！");
        result.setProperties(ImmutableMap.of("avatar", "/uploads/profile/" + url));
        return result;
    }
}
