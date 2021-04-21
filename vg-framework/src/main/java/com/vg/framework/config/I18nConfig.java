package com.vg.framework.config;

import com.vg.framework.i18n.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * 资源文件配置加载
 *
 * @author ruoyi
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {
    @Bean(name = "localeResolver")
    public MyLocaleResolver myLocaleResolver() {
        MyLocaleResolver myLocaleResolver = new MyLocaleResolver();
        myLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return myLocaleResolver;
    }
}