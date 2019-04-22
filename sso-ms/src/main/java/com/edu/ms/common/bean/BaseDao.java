package com.edu.ms.common.bean;

import java.util.List;

/**
 * 抽象Dao层
 *
 * @author wst
 * @date 2019/1/2 19:34
 **/
public interface BaseDao<T> {

    /**
     * 保存数据
     *
     * @param record 保存参数PO
     * @return 影响行数
     */
    int save(T record);

    /**
     * 根据参数查询信息
     *
     * @param record 查询参数PO
     * @return 返回参数
     */
    T queryByCondition(T record);

    /**
     * 根据参数查询信息
     *
     * @param record 查询参数PO
     * @return 返回参数
     */
    List<T> listByCondition(T record);

    /**
     * 根据id修改值
     *
     * @param record 修改参数
     * @return 影响行数
     */
    int updateById(T record);

    /**
     * 分页查询参数
     *
     * @param reqPO 查询参数PO
     * @return 返回参数
     */
    List<T> findPage(T reqPO);

    /**
     * 计算总数
     *
     * @param reqPO 查询参数PO
     * @return 返回参数
     */
    Integer countAll(T reqPO);

    /**
     * 删除数据
     * @param reqPO
     * @return
     */
    Integer deleteById(T reqPO);
}
