package com.edu.business.persistence.service.impl;

import com.edu.business.persistence.dao.UserDao;
import com.edu.business.persistence.po.UserPO;
import com.edu.business.persistence.service.UserService;
import com.edu.utils.EduResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserPO getUserInfo() {
        UserPO userPO = userDao.findUserInfo();
        return userPO;
    }

    @Override
    public UserPO selectDataByUsernameAnd(UserPO userPO) {
        return userDao.selectData(userPO);
    }

    @Override
    public UserPO getTokenByCookie(String ssoToken) {
        return userDao.getTokenByCookie(ssoToken);
    }

    @Override
    public void updateToken(String token) {
        userDao.updateToken(token);
    }

    @Override
    public void updateTokenById(UserPO userPO) {
        userDao.updateTokenById(userPO);
    }

    @Override
    public EduResult register(UserPO userPO) {
        UserPO respPO = userDao.selectDataByUsername(userPO);
        if (respPO != null) {
            return EduResult.build(501, "用户已经存在");
        }
        userDao.save(userPO);
        return EduResult.ok();
    }

}
