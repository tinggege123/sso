package com.edu.ssoserver.configuration;

import com.edu.ssoserver.em.LoginStatusEnum;
import com.edu.ssoserver.listener.NoSsoSessionListener;
import com.edu.ssoserver.listener.SsoSessionListener;
import com.edu.ssoserver.service.SsoService;
import com.edu.ssoserver.service.impl.SsoDataBaseServiceImpl;
import com.edu.ssoserver.service.impl.SsoSessionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;

/**
 * 配置注入的bean
 * (之所以使用手动注入bean,原因是在为了进行可扩展的bean加载
 * 如果在application.yml的loginStatus设置为0，那只会进行加载session的实现方法
 * 如果是1，加载database为存储介质的实现方法
 * 如果是2，加载redis为存储介质的实现方法)
 *
 * @author wst
 * @date 2018/11/18 14:28
 **/
@Slf4j
@Configuration
public class Manager {

    @Value("${sso.loginStatus}")
    private int loginStatus;

    @Bean
    public SsoService ssoService() {
        SsoService ssoService = null;
        if (LoginStatusEnum.SESSION_LOGIN.getStatus() == loginStatus) {
            log.info("[sso server 启动] 初始化的sessionBean管理");
            ssoService = new SsoSessionServiceImpl();
        } else if (LoginStatusEnum.DATABASE_LOGIN.getStatus() == loginStatus) {
            log.info("[sso server 启动] 初始化的dataBaseBean管理");
            ssoService = new SsoDataBaseServiceImpl();
        } else {
            log.info("[sso server 启动] 初始化的redisBean管理");
        }
        return ssoService;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListenerBean() {
        ServletListenerRegistrationBean<HttpSessionListener> sessionListener = null;
        //session登录和非session登录的初始化bean是不相同的
        if (LoginStatusEnum.SESSION_LOGIN.getStatus() == loginStatus) {
            sessionListener = new ServletListenerRegistrationBean<>(new SsoSessionListener());
        } else {
            sessionListener = new ServletListenerRegistrationBean<>(new NoSsoSessionListener());
        }
        return sessionListener;
    }
}
