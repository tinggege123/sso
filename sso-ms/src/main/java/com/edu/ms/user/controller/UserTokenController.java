package com.edu.ms.user.controller;

import com.edu.ms.common.bean.BaseController;
import com.edu.ms.user.po.UserTokenPO;
import com.edu.ms.user.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户token表
 *
 * @author wst
 * @date 2019/1/3 14:33
 **/
@Slf4j
@Controller
@RequestMapping("userToken")
public class UserTokenController extends BaseController<UserTokenService, UserTokenPO> {

}
