package com.vg.project.client.interceptor.config;

import com.vg.project.client.interceptor.CmsAuthCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: James
 * @Date: 2019/2/18 17:28
 * @Description:
 */
@Configuration
public class CmsWebmvcConfiguration implements WebMvcConfigurer {
    public CmsAuthCheckInterceptor getCmsAuthCheckInterceptor() {
        return new CmsAuthCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCmsAuthCheckInterceptor()).addPathPatterns("/client/api/**");
    }
}
