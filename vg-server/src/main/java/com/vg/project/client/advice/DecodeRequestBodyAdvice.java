package com.vg.project.client.advice;

import com.vg.project.client.annotation.RequestDecode;
import com.vg.project.client.utils.RSAUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @Author: James
 * @Date: 2019/3/14 11:30
 * @Description:
 */
@Component
@ControllerAdvice(basePackages = "com.vg.project.client.controller")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        //return methodParameter.getMethodAnnotation(RequestDecode.class) != null && methodParameter.getParameterAnnotation(RequestBody.class) != null;
        return methodParameter.getMethodAnnotation(RequestDecode.class) != null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage request, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        RequestDecode requestDecode = parameter.getMethodAnnotation(RequestDecode.class);
        if (requestDecode == null) {
            return inputMessage;
        }

        InputStream in = inputMessage.getBody();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        // 接收到数据
        String encryptString = sb.toString();
        // RSA解密
        return new DecodedHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(RSAUtil.serverDecrypt(encryptString).getBytes("UTF-8")));
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecodedHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        public DecodedHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}