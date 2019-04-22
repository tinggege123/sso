package com.edu.business.persistence.service;

import com.edu.business.persistence.po.UserPO;
import com.edu.utils.EduResult;

public interface UserService {

    public UserPO getUserInfo();

    UserPO selectDataByUsernameAnd(UserPO userPO);

    UserPO getTokenByCookie(String ssoToken);

    void updateToken(String token);

    void updateTokenById(UserPO userPO);

    EduResult register(UserPO userPO);


}
