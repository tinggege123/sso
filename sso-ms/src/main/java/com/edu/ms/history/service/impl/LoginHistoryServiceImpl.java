package com.edu.ms.history.service.impl;

import com.edu.ms.common.bean.BaseServiceImpl;
import com.edu.ms.history.dao.LoginHistoryDao;
import com.edu.ms.history.po.LoginHistoryPO;
import com.edu.ms.history.service.LoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 登录历史记录
 *
 * @author wst
 * @date 2018/12/26 20:14
 **/
@Slf4j
@Service
public class LoginHistoryServiceImpl extends BaseServiceImpl<LoginHistoryDao, LoginHistoryPO> implements LoginHistoryService {
}
