package com.vg.project.client.annotation;

import java.lang.annotation.*;

/**
 * @Author: James
 * @Date: 2019/3/14 11:23
 * @Description:请求数据解密
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestDecode {
}
