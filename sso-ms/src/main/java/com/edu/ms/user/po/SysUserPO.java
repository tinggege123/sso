package com.edu.ms.user.po;

import com.edu.ms.common.bean.Page;
import lombok.*;

import java.util.Date;

/**
 * t_sys_user表对应实体
 *
 * @author shengting_wang
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPO extends Page {

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