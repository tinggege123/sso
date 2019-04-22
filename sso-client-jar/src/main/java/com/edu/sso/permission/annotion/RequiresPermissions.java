package com.edu.sso.permission.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单点登录注解权限校验
 *
 * @author wst
 * @date 2018/12/15 13:25
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermissions {

    String url() default "";
}
