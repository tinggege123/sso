package com.edu.ms.log.service.impl;

import com.edu.ms.common.bean.BaseServiceImpl;
import com.edu.ms.log.dao.LogHistoryDao;
import com.edu.ms.log.po.LogHistoryPO;
import com.edu.ms.log.service.LogHistoryService;
import org.springframework.stereotype.Service;

/**
 * 日志
 *
 * @author wst
 * @date 2019/1/5 14:08
 **/
@Service
public class LogHistoryServiceImpl extends BaseServiceImpl<LogHistoryDao,LogHistoryPO> implements LogHistoryService {
}
