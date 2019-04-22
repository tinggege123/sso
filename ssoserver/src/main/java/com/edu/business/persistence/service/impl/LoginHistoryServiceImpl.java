package com.edu.business.persistence.service.impl;

import com.edu.business.persistence.dao.LoginHistoryDao;
import com.edu.business.persistence.po.LoginHistoryPO;
import com.edu.business.persistence.service.LoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录记录实现实现层
 *
 * @author wst
 * @date 2018/11/18 13:22
 **/
@Slf4j
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    @Resource
    private LoginHistoryDao loginHistoryDao;

    /**
     * @param record
     */
    @Override
    public int save(LoginHistoryPO record) {
        return loginHistoryDao.save(record);
    }

    /**
     * @param record
     */
    @Override
    public LoginHistoryPO queryByCondition(LoginHistoryPO record) {
        return loginHistoryDao.queryByCondition(record);
    }

    /**
     * @param record
     */
    @Override
    public List<LoginHistoryPO> listByCondition(LoginHistoryPO record) {
        return loginHistoryDao.listByCondition(record);
    }

    /**
     * @param record
     */
    @Override
    public int updateById(LoginHistoryPO record) {
        return loginHistoryDao.updateById(record);
    }

    @Override
    public int delete(LoginHistoryPO record) {
        return loginHistoryDao.delete(record);
    }
}
