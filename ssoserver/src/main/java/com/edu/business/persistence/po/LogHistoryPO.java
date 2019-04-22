package com.edu.business.persistence.po;

import lombok.*;

import java.util.Date;

/**
 * t_log_history表对应实体
 * 
 * @author shengting_wang
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogHistoryPO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 操作（登录，退出，验证token）
     */
    private String opearte;

    /**
     * 用户名
     */
    private String username;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 额外信息
     */
    private String desc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}