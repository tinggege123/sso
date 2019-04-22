package com.edu.controller;

import com.edu.config.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * HelloController
 *
 * @author ting
 * @date 2018年7月16日17:37:50
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping("/hello")
    @ResponseBody
    public String greeting() throws Exception {
        return "您已登录";
    }

    @RequestMapping("/gg")
    public String gg(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model, HttpServletRequest request) throws Exception {
        return "ss";
    }

}
