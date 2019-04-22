package com.edu.ms.user.service.impl;

import com.edu.ms.common.bean.BaseServiceImpl;
import com.edu.ms.user.dao.UserTokenDao;
import com.edu.ms.user.po.UserTokenPO;
import com.edu.ms.user.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户token service实现层
 *
 * @author wst
 * @date 2019/1/2 20:07
 **/
@Slf4j
@Service
public class UserTokenServiceImpl extends BaseServiceImpl<UserTokenDao, UserTokenPO> implements UserTokenService {

}
