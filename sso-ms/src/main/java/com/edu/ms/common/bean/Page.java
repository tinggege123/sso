package com.edu.ms.common.bean;

import lombok.*;

import java.util.Date;

/**
 * 分页组件
 *
 * @author wst
 * @date 2018/12/26 21:11
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page {

    /**
     * index
     */
    private Integer limit;

    /**
     * 偏移数
     */
    private Integer offset;

    /**
     * 开始时间
     */
    private Date createTimeStart;

    /**
     * 结束时间
     */
    private Date createTimeEnd;
}
