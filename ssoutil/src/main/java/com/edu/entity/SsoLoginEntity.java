package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述
 *
 * @author wst
 * @date 2018/9/23 16:04
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsoLoginEntity implements Serializable {

    /**
     * 用户组
     */
    private String userGroup;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 请求到server的url
     */
    private String toUrl;

    /**
     * client项目的url
     */
    private String callback;

    /**
     * 最后登录成功需要调整的页面
     */
    private String loginSuccessHtml;
}
