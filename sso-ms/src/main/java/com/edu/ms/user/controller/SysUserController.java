package com.edu.ms.user.controller;

import com.edu.ms.common.bean.BaseController;
import com.edu.ms.configuration.ThreadPooTaskExecutorConfig;
import com.edu.ms.user.po.SysUserPO;
import com.edu.ms.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author 王生挺
 * @version 1.0
 * @date 2018年4月14日 16:26:25
 */
@Slf4j
@Controller
@RequestMapping("table")
public class SysUserController extends BaseController<SysUserService, SysUserPO> {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @RequestMapping("/login")
    public String toIndex() throws ExecutionException, InterruptedException {
        Future<String> future = threadPoolTaskExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.print("call");
                TimeUnit.SECONDS.sleep(1);
                return "str";
            }
        });
        String s = future.get();
        log.info("[sso SsoEncrypt]");
        return "main/top";
    }

}
