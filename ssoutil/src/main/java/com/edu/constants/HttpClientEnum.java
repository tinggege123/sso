package com.edu.constants;

/**
 * 自定义http请求的code
 *
 * @author wst
 * @date 2018/9/28 15:54
 **/
public enum HttpClientEnum {

    SUCCESS(200,"请求成功"),
    SELF_BUILD(500,"自定义错误"),
    PARAM_ERR(506,"参数有误"),
    SESSION_NO(507,"session不存在"),
    TOKEN_ERR(508,"token验证失败");

    public final int code;
    public final String msg;

    HttpClientEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
