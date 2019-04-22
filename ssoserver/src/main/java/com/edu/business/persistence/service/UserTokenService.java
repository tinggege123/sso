package com.edu.business.persistence.service;

import com.edu.business.persistence.po.UserTokenPO;

import java.util.List;

/**
 * 用户令牌保存处
 *
 * @author wst
 * @date 2018/11/24 13:41
 **/
public interface UserTokenService {

    int save(UserTokenPO userTokenPO);

    UserTokenPO getUserToken(UserTokenPO userTokenPO);

    int updateUSerTokenById(UserTokenPO userTokenPO);

    List<UserTokenPO> listByCondition(UserTokenPO userTokenPO);

    int delete(UserTokenPO userTokenPO);
}
