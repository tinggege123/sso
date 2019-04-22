package com.edu.ms.project.po;

import com.edu.ms.common.bean.Page;
import lombok.*;

import java.util.Date;

/**
 * t_project_setting表对应实体
 * 
 * @author shengting_wang
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSettingPO extends Page {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 子系统的域名
     */
    private String clientDomain;

    /**
     * 子系统的端口
     */
    private String clientPort;

    /**
     * 登录中心的域名
     */
    private String serverDomain;

    /**
     * 登录中心的端口
     */
    private String serverPort;

    /**
     * 子系统的公钥
     */
    private String publicKey;

    /**
     * 登录中心的私钥
     */
    private String privateKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}