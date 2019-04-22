package com.edu;



import com.edu.sso.interceptor.SsoInterceptor;
import com.edu.sso.listener.SsoSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan("com.edu")
public class SsoClientThirdApplication extends WebMvcConfigurerAdapter {

    @Autowired
    private SsoInterceptor ssoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器和拦截路径，此处对所有请求进行拦截，除了登录界面和登录接口
        registry.addInterceptor(ssoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns( "/ssoClient/**");
    }

	@Bean
	public ServletListenerRegistrationBean<SsoSessionListener> sessionListenerBean(){
		ServletListenerRegistrationBean<SsoSessionListener>
				sessionListener = new ServletListenerRegistrationBean<SsoSessionListener>(new SsoSessionListener());
		return sessionListener;
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoClientThirdApplication.class, args);
	}
}
