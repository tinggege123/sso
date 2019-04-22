package com.edu.controller;

import com.edu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HelloController
 *
 * @author ting
 * @date 2018年7月16日17:37:50
 */
@Slf4j
@RestController
public class ClientHelloController {

    @Autowired
    private UserService userService;


    /**
     * 应用的测试
     *
     * @param name
     * @param model
     * @param request
     * @return html名称
     * @throws Exception
     */
    @RequestMapping("/hello")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
//        response.setStatus(302);
//        HttpSession session = request.getSession();
//        String id = session.getId();
//        session.setAttribute("ting","123456");
//        log.info("view={}",id);
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //        redisUtil.set("now","哈哈");
//        System.out.println(request.getServerPort()+redisUtil.get("now").toString());
//        model.addAttribute("name", "听歌");
//        User s = userService.getUserInfo();
//        System.out.println(s.getAge());
//        response.sendRedirect("http://localhost/server/register.html");
//        SsoLoginEntity ssoLoginEntity = SsoLoginEntity.builder().callback(ClientContants.SERVER_IP_PORT).toUrl(FinalData.THIS_IP+":8081"+"/ssoServer/auth").build();
        return "您已登录";
    }

    @RequestMapping("/nowIndex")
    @ResponseBody
    public String gg(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model, HttpServletRequest request) throws Exception {
        return "index";
    }

}
