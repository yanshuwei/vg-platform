package com.vg.project.client.annotation;

import java.lang.annotation.*;

/**
 * @Author: James
 * @Date: 2019/3/14 11:23
 * @Description:响应数据加密
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseEncode {
}
