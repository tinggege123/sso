package com.edu.ms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池初始化配置类
 *
 * @author shengting_wang
 * @Date 2018/5/20 21:58
 */
@Configuration
public class ThreadPooTaskExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor summaryExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        return executor;
    }

}
