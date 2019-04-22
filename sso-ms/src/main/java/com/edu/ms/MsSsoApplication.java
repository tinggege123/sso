package com.edu.ms;

import com.edu.ms.html.interceptor.MsSsoInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;


/**
 * 项目启动类
 *
 * @author wst
 * @date 2018/9/23 14:19
 **/
@Slf4j
@SpringBootApplication
public class MsSsoApplication extends WebMvcConfigurerAdapter {

    @Resource
    private MsSsoInterceptor msSsoInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(MsSsoApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器和拦截路径，此处对所有请求进行拦截，除了登录界面和登录接口
        registry.addInterceptor(msSsoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/loginPage", "/login");
    }

}
