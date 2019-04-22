package com.edu.ssoserver.bo;

import lombok.*;

/**
 * 客户端请求服务器响应BO
 *
 * @author wst
 * @date 2018/11/18 15:04
 **/
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthBO {

    /**
     * 处理登录的方式(0:session,1:database,2:redis)
     */
    private int loginStatus;

    /**
     * 返回的url
     */
    private String redirectUrl;


}
