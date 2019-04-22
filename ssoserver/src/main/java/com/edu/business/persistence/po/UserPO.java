package com.edu.business.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户账号（手机号码）
     */
    private String username;

    /**
     * 用户昵称
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户是否可用
     */
    private String isUse;

    /**
     * 登录日期
     */
    private Date loginDate;

    /**
     * 加密的salt
     */
    private String salt;

    /**
     * 移动端登录的令牌
     */
    private String token;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
