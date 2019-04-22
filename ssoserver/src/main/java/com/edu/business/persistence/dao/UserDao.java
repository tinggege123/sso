package com.edu.business.persistence.dao;

import com.edu.business.persistence.po.UserPO;

public interface UserDao {

    UserPO findUserInfo();

    UserPO selectData(UserPO userPO);

    UserPO selectDataByUsername(UserPO userPO);

    UserPO getTokenByCookie(String ssoToken);

    void updateToken(String token);

    void updateTokenById(UserPO userPO);

    int save(UserPO userPO);
}
