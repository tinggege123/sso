package com.edu.business.persistence.service;

import com.edu.business.persistence.po.LoginHistoryPO;

import java.util.List;

/**
 * 登录历史描述表
 *
 * @author wst
 * @date 2018/11/18 13:21
 **/
public interface LoginHistoryService {

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

    int delete(LoginHistoryPO record);

}
