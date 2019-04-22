package com.edu;


import com.edu.sso.interceptor.SsoInterceptor;
import com.edu.sso.listener.SsoSessionListener;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@SpringBootApplication
public class SsoClientApplication extends WebMvcConfigurerAdapter {

	@Resource
	private SsoInterceptor ssoInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加自定义拦截器和拦截路径，此处对所有请求进行拦截，除了登录界面和登录接口
		registry.addInterceptor(ssoInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns( "/ssoClient/**","/welcome","/static/**");
	}
	@Bean
	public ServletListenerRegistrationBean<SsoSessionListener> sessionListenerBean(){
		ServletListenerRegistrationBean<SsoSessionListener>
				sessionListener = new ServletListenerRegistrationBean<SsoSessionListener>(new SsoSessionListener());
		return sessionListener;
	}
	@Bean(name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
		beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
		beetlGroupUtilConfiguration.init();
		return beetlGroupUtilConfiguration;
	}
	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(
			@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setPrefix("/view/");
		beetlSpringViewResolver.setSuffix(".html");
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		return beetlSpringViewResolver;
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoClientApplication.class, args);
	}
}
