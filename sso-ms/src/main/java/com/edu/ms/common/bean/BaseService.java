package com.edu.ms.common.bean;

import java.util.List;

/**
 * 抽象service接口层
 *
 * @author wst
 * @date 2019/1/2 19:33
 **/
public interface BaseService<T> {

    /**
     * @param record
     */
    int save(T record);

    /**
     * @param record
     */
    T queryByCondition(T record);

    /**
     * @param record
     */
    List<T> listByCondition(T record);

    /**
     * @param record
     */
    int updateById(T record);

    /**
     * 分页查询参数
     *
     * @param reqPO
     * @return
     */
    List<T> findPage(T reqPO);

    /**
     * 计算总数
     *
     * @param reqPO
     * @return
     */
    Integer countAll(T reqPO);

    /**
     * 删除数据
     * @param id
     * @return
     */
    Integer deleteById(T id);
}
