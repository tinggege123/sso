package com.edu.ms.history.controller;

import com.edu.ms.common.bean.BaseController;
import com.edu.ms.history.po.LoginHistoryPO;
import com.edu.ms.history.service.LoginHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 子系统登录记录
 *
 * @author wst
 * @date 2019/1/13 16:51
 **/
@Controller
@RequestMapping("loginHistory")
public class LoginHistoryController extends BaseController<LoginHistoryService, LoginHistoryPO> {
}
