package com.edu.ms.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 前端分页数据
 *
 * @author wst
 * @date 2018/12/29 11:24
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataGrid<T> {

    /**
     * list值
     */
    private List<T> rows;

    /**
     * 总数
     */
    private Integer total;
}
