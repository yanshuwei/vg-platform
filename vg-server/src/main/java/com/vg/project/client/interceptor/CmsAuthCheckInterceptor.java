package com.vg.project.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.vg.project.client.dto.user.UserDto;
import com.vg.project.client.utils.JWTUtil;
import com.vg.project.client.utils.RSAUtil;
import com.vg.project.client.utils.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @Author: James
 * @Date: 2019/2/18 17:33
 * @Description:
 */
public class CmsAuthCheckInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {
    private NamedThreadLocal<StopWatch> stopWatchThreadLocal = new NamedThreadLocal<>("ConsumeTime-StopWatch");
    private static final Logger logger = LoggerFactory.getLogger(CmsAuthCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        super.preHandle(request, response, handler);
        UUID uuid = UUID.randomUUID();
        StopWatch stopWatch = new StopWatch();
        // 绑定线程变量
        stopWatchThreadLocal.set(stopWatch);
        stopWatch.start(uuid.toString());

        // 获取请求头部信息
        String token = request.getHeader("token");
        String userId = request.getHeader("userId");

        ResponseData result = null;

        if (StringUtils.isBlank(token) || StringUtils.isBlank(userId)) {
            result = new ResponseData("200001", "请求参数不完整，请返回重试！");
            responseMessage(response, result);
            return false;
        }

        UserDto user = JWTUtil.unsign(token, UserDto.class);
        if (user != null) {
            if (user.getUserId() != Long.parseLong(userId)) {
                result = new ResponseData("200002", "非法的请求参数，请返回重试！");
            }
        } else {
            result = new ResponseData("200003", "会话已过期或无效的请求参数，请重新登录！");
        }

        if (result != null) {
            responseMessage(response, result);
            return false;
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        StopWatch stopWatch = stopWatchThreadLocal.get();
        stopWatch.stop();
        logger.info("URL:{}.请求执行时间:{}ms", request.getRequestURL(), stopWatch.getTotalTimeMillis());
        stopWatchThreadLocal.set(null);
    }

    /**
     * 向客户端返回错误信息
     *
     * @param response
     * @param result
     * @throws IOException
     */
    private void responseMessage(HttpServletResponse response, ResponseData result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.append(RSAUtil.serverEncrypt(JSON.toJSONString(result)));
        out.flush();
        out.close();
    }
}
