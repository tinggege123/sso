package com.edu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HelloController
 *
 * @author ting
 * @date 2018年7月16日17:37:50
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping("main")
    public String main() {
        return "main";
    }

    @RequestMapping("contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping("products")
    public String products() {
        return "products";
    }

    @RequestMapping("services")
    public String services() {
        return "services";
    }

    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }


}
