package com.vg.project.client.advice;

import com.alibaba.fastjson.JSON;
import com.vg.project.client.annotation.ResponseEncode;
import com.vg.project.client.utils.RSAUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.regex.Pattern;

/**
 * @Author: James
 * @Date: 2019/3/14 13:00
 * @Description:
 */
@Component
@ControllerAdvice(basePackages = "com.vg.project.client.controller")
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethodAnnotation(ResponseEncode.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String jsonString = JSON.toJSONString(body);

        /**********************************2020广州物流展特殊处理Start**********************************/
        String userId = serverHttpRequest.getHeaders().getFirst("userId");
        String url = serverHttpRequest.getURI().getPath();

        if ("47".equals(userId)) {
            if ("/client/api/projects".equals(url)) {
                jsonString = jsonString.replaceAll("equipmentName\":\"圆通", "equipmentName\":\"金峰")
                        .replaceAll("equipmentName\":\"申通", "equipmentName\":\"金峰")
                        .replaceAll("equipmentName\":\"顺丰", "equipmentName\":\"金峰")
                        .replaceAll("operatorName\":\"申通", "operatorName\":\"运营商1")
                        .replaceAll("operatorName\":\"圆通", "operatorName\":\"运营商2")
                        .replaceAll("operatorName\":\"顺丰", "operatorName\":\"运营商3")
                        .replaceAll("projectName\":\"申通", "projectName\":\"金峰")
                        .replaceAll("projectName\":\"圆通", "projectName\":\"金峰")
                        .replaceAll("projectName\":\"顺丰", "projectName\":\"金峰")
                        .replaceAll("^(/uploads/profile/){1}([a-z0-9])+(\\.jpg){1}", "/uploads/profile/a1f10371d2e0c051dab6424ceb9cec07.jpg");
            } else if ("/client/api/dayStatistics/searchData".equals(url) || "/client/api/dayStatistics".equals(url)
                    || "/client/api/proGather".equals(url) || "/client/api/opertaionGather".equals(url)) {
                jsonString = jsonString.replaceAll("申通", "运营商1").replaceAll("橙联", "运营商2")
                        .replaceAll("圆通", "运营商3").replaceAll("韵达", "运营商4")
                        .replaceAll("百世", "运营商5").replaceAll("苏宁", "运营商6")
                        .replaceAll("顺丰", "运营商7").replaceAll("德邦", "运营商8")
                        .replaceAll("虾皮", "运营商9").replaceAll("邮政", "运营商10")
                        .replaceAll("^(/uploads/profile/){1}([a-z0-9])+(\\.jpg){1}", "/uploads/profile/a1f10371d2e0c051dab6424ceb9cec07.jpg");
            }

            jsonString = Pattern.compile("(/uploads/profile/){1}([a-z0-9])+(\\.jpg){1}").matcher(jsonString).replaceAll("/uploads/profile/a1f10371d2e0c051dab6424ceb9cec07.jpg");
        }
        /**********************************2020广州物流展特殊处理End**********************************/

        // jsonString RSA加密
        String encryptString = null;
        try {
            // RSA加密
            encryptString = RSAUtil.serverEncrypt(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptString;
    }
}
