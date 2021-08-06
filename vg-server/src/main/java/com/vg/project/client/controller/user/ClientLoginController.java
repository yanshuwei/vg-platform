package com.vg.project.client.controller.user;

import com.vg.common.utils.StringUtils;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.dto.user.UserDto;
import com.vg.project.client.utils.JWTUtil;
import com.vg.project.client.utils.ResponseData;
import com.vg.project.system.menu.service.IMenuService;
import com.vg.project.system.role.service.IRoleService;
import com.vg.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;


/**
 * 登录验证
 *
 * @author James
 */
@Controller
public class ClientLoginController extends BaseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;

    @RequestDecode
    @ResponseEncode
    @ResponseBody
    @PostMapping(value = "/client/login")
    public ResponseData login(@RequestBody UserDto dto) {
        ResponseData result;

        try {
            if (StringUtils.isBlank(dto.getUserName()) || StringUtils.isBlank(dto.getPassword())) {
                result = new ResponseData("100001", "用户名、密码不能为空！");
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(dto.getUserName(), dto.getPassword(), false);
                Subject subject = SecurityUtils.getSubject();
                //subject.getSession().setTimeout(60000);
                subject.login(token);

                User user = (User) SecurityUtils.getSubject().getPrincipal();
                BeanUtils.copyProperties(user, dto);
                dto.setPhone(user.getPhonenumber());
                dto.setAvatar("/uploads/profile/" + user.getAvatar());
                dto.setPassword(null);

                // 获取用户角色列表
                Set<String> roles = roleService.selectRoleKeys(user.getUserId());
                dto.setRoles(roles);
                // 获取用户权限列表
                Set<String> permissions = menuService.selectPermsByUserId(user.getUserId());
                dto.setPermissions(permissions);

                //用户类型
                dto.setUserType(user.getUserType());

                // 生成token
                String api_token = JWTUtil.sign(dto, 60 * 60 * 1000);
                dto.setToken(api_token);

                result = new ResponseData("10000", "用户登录验证通过！");
                result.setProperties(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "用户名或密码错误！");
        }

        return result;
    }
}
