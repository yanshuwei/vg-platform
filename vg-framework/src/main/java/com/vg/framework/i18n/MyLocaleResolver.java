package com.vg.framework.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Author: James
 * @Date: 2019/8/12 17:57
 * @Description:
 */
public class MyLocaleResolver extends AcceptHeaderLocaleResolver {
    private Locale myLocal;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang =request.getParameter("lang");
        if (!StringUtils.isEmpty(lang)) {
            String[] split = lang.split("_");
            myLocal = new Locale(split[0], split[1]);
        }
        return myLocal == null ? request.getLocale() : myLocal;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        myLocal = locale;
    }
}