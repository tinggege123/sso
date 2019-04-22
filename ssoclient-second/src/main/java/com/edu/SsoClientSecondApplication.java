package com.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan("com.edu")
public class SsoClientSecondApplication extends WebMvcConfigurerAdapter {


    public static void main(String[] args) {
        SpringApplication.run(SsoClientSecondApplication.class, args);
    }
}
