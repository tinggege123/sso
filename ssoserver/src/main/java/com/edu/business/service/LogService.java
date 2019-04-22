package com.edu.business.service;

import com.edu.business.persistence.po.LogHistoryPO;

/**
 * 接口层统一调用
 *
 * @author wst
 * @date 2019/3/10 16:48
 **/
public interface LogService {

    /**
     * 保存日志
     *
     * @param logHistoryPO
     */
    void saveLog(LogHistoryPO logHistoryPO);
}
