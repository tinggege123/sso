package com.edu.business.persistence.dao;

import com.edu.business.persistence.po.LogHistoryPO;

import java.util.List;

/**
 * t_log_history表对应实体
 * 
 * @author shengting_wang
 */
public interface LogHistoryDao {

    /**
     * @param record
     */
    int save(LogHistoryPO record);

    /**
     * @param record
     */
    LogHistoryPO queryByCondition(LogHistoryPO record);

    /**
     * @param record
     */
    List<LogHistoryPO> listByCondition(LogHistoryPO record);

    /**
     * @param record
     */
    int updateById(LogHistoryPO record);

    /**
     * @param record
     */
    int deleteById(LogHistoryPO record);

}