package com.edu.ssoserver.bo;

import lombok.*;

/**
 * 登录逻辑BO
 *
 * @author wst
 * @date 2018/11/18 16:35
 **/
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginBO {

    /**
     * 处理登录的方式(0:session,1:database,2:redis)
     */
    private int loginStatus;

    /**
     * 返回的url
     */
    private String redirectUrl;
}
