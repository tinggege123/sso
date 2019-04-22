package com.edu.ms.log.po;

import com.edu.ms.common.bean.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * t_log_history表对应实体
 * 
 * @author shengting_wang
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogHistoryPO extends Page {

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}