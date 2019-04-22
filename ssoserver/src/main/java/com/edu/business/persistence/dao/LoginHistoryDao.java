package com.edu.business.persistence.dao;

import com.edu.business.persistence.po.LoginHistoryPO;
import java.util.List;

/**
 * t_login_history表对应实体
 * 
 * @author shengting_wang
 */
public interface LoginHistoryDao {

    /**
     * @param record
     */
    int save(LoginHistoryPO record);

    /**
     * @param record
     */
    LoginHistoryPO queryByCondition(LoginHistoryPO record);

    /**
     * @param record
     */
    List<LoginHistoryPO> listByCondition(LoginHistoryPO record);

    /**
     * @param record
     */
    int updateById(LoginHistoryPO record);

    /**
     * 根据IpPort进行修改数据
     * @param record
     * @return
     */
    int updateByIpPort(LoginHistoryPO record);

    /**
     * 删除数据
     * @param record
     * @return
     */
    int delete(LoginHistoryPO record);

}