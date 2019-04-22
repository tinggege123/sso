package com.edu.business.persistence.service.impl;

import com.edu.business.persistence.dao.UserTokenDao;
import com.edu.business.persistence.po.UserTokenPO;
import com.edu.business.persistence.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.AbstractList;
import java.util.List;

/**
 * 描述
 *
 * @author wst
 * @date 2018/11/25 15:36
 **/
@Slf4j
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private UserTokenDao userTokenDao;

    @Override
    public int save(UserTokenPO userTokenPO) {
        return userTokenDao.save(userTokenPO);
    }

    @Override
    public UserTokenPO getUserToken(UserTokenPO userTokenPO) {
        return userTokenDao.queryByCondition(userTokenPO);
    }

    @Override
    public int updateUSerTokenById(UserTokenPO userTokenPO) {
        return userTokenDao.updateById(userTokenPO);
    }

    @Override
    public List<UserTokenPO> listByCondition(UserTokenPO userTokenPO) {
        return userTokenDao.listByCondition(userTokenPO);
    }

    @Override
    public int delete(UserTokenPO userTokenPO) {
        return userTokenDao.delete(userTokenPO);
    }
}
