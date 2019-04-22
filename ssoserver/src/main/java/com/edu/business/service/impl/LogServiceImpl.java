package com.edu.business.service.impl;

import com.edu.business.persistence.dao.LogHistoryDao;
import com.edu.business.persistence.dao.UserTokenDao;
import com.edu.business.persistence.po.LogHistoryPO;
import com.edu.business.persistence.po.UserTokenPO;
import com.edu.business.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 实现层统一调用
 *
 * @author wst
 * @date 2019/3/10 16:49
 **/
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private LogHistoryDao logHistoryDao;

    @Resource
    private UserTokenDao userTokenDao;

    @Override
    public void saveLog(LogHistoryPO logHistoryPO) {
        //开启一个线程进行异步保存
        threadPoolTaskExecutor.submit(
                () -> saveLogData(logHistoryPO));
    }

    private void saveLogData(LogHistoryPO logHistoryPO) {
        //参数校验
        if (logHistoryPO == null || StringUtils.isBlank(logHistoryPO.getUsername()) || StringUtils.isBlank(logHistoryPO.getOpearte())) {
            log.warn("[查询用户令牌信息] 参数验证失败");
            return;
        }
        //替换名字
        try {
            UserTokenPO userTokenPO = userTokenDao.queryByCondition(UserTokenPO.builder().token(logHistoryPO.getUsername()).build());
            if (userTokenPO != null && StringUtils.isNotBlank(userTokenPO.getUsername())) {
                logHistoryPO.setUsername(userTokenPO.getUsername());
            }
        } catch (Exception e) {
            log.error("[查询用户令牌信息] 异常 req={}", logHistoryPO, e);
        }
        //保存数据
        try {
            logHistoryDao.save(logHistoryPO);
        } catch (Exception e) {
            log.error("[异步保存日志] 异常 req={}", logHistoryPO, e);
        }
    }
}
