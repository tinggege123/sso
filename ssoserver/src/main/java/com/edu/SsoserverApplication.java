package com.edu;

import com.edu.ssoserver.interceptor.SsoServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;


/**
 * 该项目启动类
 *
 * @author wst
 * @date 2018/9/23 14:19
 **/
@Slf4j
@SpringBootApplication
public class SsoserverApplication extends WebMvcConfigurerAdapter {

    @Resource
    private SsoServerInterceptor ssoServerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器和拦截路径，此处对所有请求进行拦截，除了登录界面和登录接口
        registry.addInterceptor(ssoServerInterceptor)
                .addPathPatterns("/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoserverApplication.class, args);
    }
}
