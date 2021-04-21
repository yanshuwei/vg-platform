package com.vg.project.system;

import com.vg.framework.config.RuoYiConfig;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.shiro.util.ShiroUtils;
import com.vg.project.shiro.util.TreeUtils;
import com.vg.project.system.menu.domain.Menu;
import com.vg.project.system.menu.service.IMenuService;
import com.vg.project.system.user.domain.User;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;


    // 系统首页
    @GetMapping("/index")
    public String index(HttpServletRequest request, ModelMap mmap) {
        // 取身份信息
        User user = ShiroUtils.getUser();
        Set<String> permSet = menuService.selectPermsByUserId(user.getUserId());
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUserId(user.getUserId());
        // 根据权限字符串添加自定义菜单
        StringBuilder perms = new StringBuilder();
        /*if("Platform".equals(user.getUserType())){
            perms.append("system:manhourStatistics:myhour");
        }*/
        List<Menu> diyMenus = menuService.selectMenusByPerms(perms.toString());
        diyMenus.stream().sequential().collect(Collectors.toCollection(() -> menus));
        List<Menu> finalMenus = menus;
        //去重
        finalMenus = finalMenus.stream().distinct().collect(Collectors.toList());
        mmap.put("menus", TreeUtils.getChildPerms(finalMenus, 0));
        mmap.put("user", user);
        mmap.put("datav", permSet.contains("system:data:view"));
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        return "index";
    }

    @RequiresPermissions("system:data:view")
    @GetMapping("/data/view")
    public String dataView(ModelMap mmap) {
        User user = ShiroUtils.getUser();
        mmap.put("operations", null);
        mmap.put("projects", null);
        return "map";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", ruoYiConfig.getVersion());
        return "mindex";
    }

    @RequestMapping("/status")
    @ResponseBody
    public void status(HttpServletResponse response, HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> list = new HashMap<>();
        //将list装换为Json数组（JSONArray）
        String json = JSONArray.fromObject(list).toString();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json; charset=utf-8");
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
