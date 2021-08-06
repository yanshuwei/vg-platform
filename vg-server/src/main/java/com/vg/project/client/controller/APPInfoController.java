package com.vg.project.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.vg.framework.web.controller.BaseController;
import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.utils.ResponseData;
import com.vg.project.system.app.domain.FeedBack;
import com.vg.project.system.app.domain.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: James
 * @Date: 2019/4/29 10:10
 * @Description:
 */
@RestController
public class APPInfoController extends BaseController {

    /*
    *
    * 版本更新校验
    * */
    @PostMapping("/client/api/check_version")
    @ResponseEncode
    @RequestDecode
    public ResponseData checkVersion(@RequestBody JSONObject object) {
        String appId = object.getString("appId");
        ResponseData result = new ResponseData("10000", "版本校验成功！");
        // TODO  根据appId动态获取最新提交的版本、特性、下载地址sys_app_version
        result = new ResponseData("100000", "版本校验成功！");
        return result;
    }
}
