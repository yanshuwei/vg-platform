package com.vg.project.client.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.vg.common.utils.http.HttpUtils;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.dto.user.UserDto;
import com.vg.project.client.dto.user.WeChatDto;
import com.vg.project.client.utils.JWTUtil;
import com.vg.project.client.utils.ResponseData;
import com.vg.project.shiro.service.PasswordService;
import com.vg.project.system.menu.service.IMenuService;
import com.vg.project.system.role.service.IRoleService;
import com.vg.project.system.user.domain.User;
import com.vg.project.system.user.service.IUserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author: James
 * @Date: 2019/4/29 10:47
 * @Description:
 */
@RestController
public class WeChatController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;

    /**
     * 微信账号绑定
     *
     * @param weChatDto
     * @return*/

    //@PostMapping({"/client/wx_bind", "/client/api/wx_bind"})
    @PostMapping("/client/wx_bind")
    @RequestDecode
    @ResponseEncode
    public ResponseData bindWeChat(@RequestBody WeChatDto weChatDto) {
        ResponseData result;
        //用户登录名称
        String userName = weChatDto.getUserName();
        String password = weChatDto.getPassword();
        //String wx_openid = weChatDto.getWx_openid();
        String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + weChatDto.getWx_code() + "&grant_type=authorization_code";
        String str = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject object = JSONObject.parseObject(str);
        String wx_openid = object.getString("openid");

        try {
            User user = userService.selectUserByWxOpenId(wx_openid);
            if (user != null) {
                if (!user.getLoginName().equals(userName)) {
                    result = new ResponseData("100004", "微信已绑定其他账号！");
                } else {
                    result = new ResponseData("100005", "微信账号已绑定！");
                }
            } else {
                // 校验用户名、密码是否正确
                user = userService.selectUserByLoginName(userName);
                if (user == null) {
                    result = new ResponseData("100003", "用户账号不存在！");
                } else if (passwordService.matches(user, password)) {
                    User u = userService.selectUserByLoginName(userName);
                    if(u.getWxOpenId()!=null){
                        result = new ResponseData("100006", "用户账号已绑定其他微信账号！");
                    }else{
                        user.setWxOpenId(wx_openid);
                        userService.updateUserInfo(user);
                        result = new ResponseData("10000", "微信账号绑定成功！");
                    }

                } else {
                    result = new ResponseData("100002", "用户账号密码不正确！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100001", "微信账号绑定失败！");
        }
        return result;
    }

    /**
     * 微信登录
     *
     * @param weChatDto
     * @return
     */
    @PostMapping("/client/wx_login")
    @ResponseEncode
    @RequestDecode
    public ResponseData weChatLogin(@RequestBody WeChatDto weChatDto) {
        ResponseData result;
        String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + weChatDto.getWx_code() + "&grant_type=authorization_code";
        String str = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject object=JSONObject.parseObject(str);
        try {
            User user = userService.selectUserByWxOpenId(object.getString("openid"));
            if (user == null) {
                result = new ResponseData("100001", "微信账号未绑定！");
            } else {
                String userName = user.getUserName();

                UserDto dto = new UserDto();
                BeanUtils.copyProperties(user, dto);

                dto.setUserName(userName);
                dto.setPassword(null);
                dto.setPhone(user.getPhonenumber());
                dto.setAvatar("/uploads/profile/" + user.getAvatar());


                // 获取用户角色列表
                Set<String> roles = roleService.selectRoleKeys(user.getUserId());
                // 获取用户权限列表
                Set<String> permissions = menuService.selectPermsByUserId(user.getUserId());
                dto.setRoles(roles);
                dto.setPermissions(permissions);

                // 生成token
                String api_token = JWTUtil.sign(dto, 60 * 60 * 1000);
                dto.setToken(api_token);

                result = new ResponseData("10000", "用户登录验证通过！");
                result.setProperties(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "登录失败！");
        }
        return result;
    }

    /**
     *  获取微信小程序openid
     * @param weChatDto
     * @return
     */
    @PostMapping("/client/getId")
    @ResponseEncode
    @RequestDecode
    public ResponseData getOpenid(@RequestBody WeChatDto weChatDto) {
        ResponseData result;
        try {
            String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + weChatDto.getWx_code() + "&grant_type=authorization_code";
            String str = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
            JSONObject object=JSONObject.parseObject(str);
            String wx_openid = object.getString("openid");
            result = new ResponseData("10000", "获取成功！");
            result.setProperties(ImmutableMap.of("openid",wx_openid));

        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseData("100002", "获取失败！");
        }
        return result;
    }
    /***
     * 检查当前用户是否已绑定微信
     */
    @PostMapping("/client/wx_checkBind")
    @ResponseEncode
    @RequestDecode
    public ResponseData checkBind(@RequestBody User u){
        ResponseData result ;
        User user=userService.selectUserByLoginName(u.getLoginName());
        if(user==null){
            result=new ResponseData("100001","此用户不存在！");
        }else{
            if(user.getWxOpenId()==null){
                result=new ResponseData("100002","此用户尚未绑定微信！");
            }else{
                result=new ResponseData("10000","此用户已绑定微信！");
            }
        }
        return result;
    }
}
