package com.edu.ms.log.controller;

import com.edu.ms.common.bean.BaseController;
import com.edu.ms.log.po.LogHistoryPO;
import com.edu.ms.log.service.LogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志
 *
 * @author wst
 * @date 2019/1/5 14:10
 **/
@Slf4j
@Controller
@RequestMapping("logHistory")
public class LogHistoryController extends BaseController<LogHistoryService, LogHistoryPO> {
}
