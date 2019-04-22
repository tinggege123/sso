package com.edu.ssoserver.em;

import lombok.Getter;

/**
 * 登录状态的枚举
 *
 * @author wst
 * @date 2018/11/18 13:41
 **/
@Getter
public enum LoginStatusEnum {

    SESSION_LOGIN(0,"session登录"),
    DATABASE_LOGIN(1,"数据库进行存储登录"),
    REDIS_LOGIN(2,"redis进行存储进行登录");

    private int status;

    private String msg;

    LoginStatusEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    }
