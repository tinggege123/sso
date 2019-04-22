package com.edu.ssoserver.em;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 0表示为一位用户只能在一个终端登录，1表示一个用户可以在多个终端登录
 *
 * @author wst
 * @date 2018/11/24 14:31
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  MultiUserLoginEnum {

    SINGLE(0,"单终端登录"),
    MULTI(1,"多终端登录");

    private int code;

    private String msg;
}
