package com.edu.business.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * t_login_history表对应实体
 * 
 * @author shengting_wang
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryPO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 用户名
     */
    private String username;

    /**
     * ip加端口
     */
    private String ipPort;

    /**
     * 子系统的sessionid
     */
    private String sessionId;

    /**
     * 令牌
     */
    private String token;

    /**
     * 状态(1:表示在登录状态，0:表示已经退出)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}