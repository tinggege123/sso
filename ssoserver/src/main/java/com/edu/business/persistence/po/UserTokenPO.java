package com.edu.business.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * t_user_token表对应实体
 * 
 * @author shengting_wang
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenPO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 服务器端的sessionId(只有session模式的时候才使用)
     */
    private String serverSessionId;

    /**
     * 令牌
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