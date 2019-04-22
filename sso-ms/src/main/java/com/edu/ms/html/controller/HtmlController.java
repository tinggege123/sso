package com.edu.ms.html.controller;

import com.edu.ms.user.po.SysUserPO;
import com.edu.ms.user.service.SysUserService;
import com.edu.ms.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 前端之地映射
 *
 * @author wst
 * @date 2019/1/13 15:31
 **/
@Slf4j
@Controller
public class HtmlController {

    @Resource
    private SysUserService sysUserService;

    private static final String ADMIN = "admin";
    @RequestMapping("/")
    public String nowDeafault() {
        return "main/index";
    }

    @RequestMapping("index")
    public String index() {
        return "main/index";
    }

    @RequestMapping("user")
    public String user() {
        return "main/user";
    }

    @RequestMapping("userToken")
    public String userToken() {
        return "main/userToken";
    }

    @RequestMapping("loginHistory")
    public String loginHistory() {
        return "main/loginHistory";
    }

    @RequestMapping("log")
    public String log() {
        return "main/log";
    }

    @RequestMapping("loginPage")
    public String loginPage() {
        return "main/loginPage";
    }

    @RequestMapping("project")
    public String project() {
        return "main/project";
    }

    @RequestMapping("login")
    public String login(SysUserPO sysUserPO, Model view) {
        log.info("[sso后台登录] 登录开始 req={} ", sysUserPO);
        //数据防守
        if (sysUserPO == null
                || StringUtils.isBlank(sysUserPO.getUsername())
                || StringUtils.isBlank(sysUserPO.getPassword())) {
            view.addAttribute("msg", "账号或密码不可为空");
            log.warn("[sso后台登录] 账号或密码不可为空");
            return "main/loginPage";
        }
        //管理员防守
        if (!ADMIN.equals(sysUserPO.getUsername())) {
            view.addAttribute("msg", "该系统只可管理员登录");
            log.warn("[sso后台登录] 该系统只可管理员登录");
            return "main/loginPage";
        }
        try {
            SysUserPO queryPO = sysUserService.queryByCondition(sysUserPO);
            if (queryPO != null) {
                SessionUtils.setLoginStatus();
                log.info("[sso后台登录] 登录成功 req={} ", sysUserPO);
                return "main/index";
            }
            log.warn("[sso后台登录] 登录失败 req={}", sysUserPO);
            view.addAttribute("msg", "账号或密码不正确");
        } catch (Exception e) {
            log.error("[sso后台登录] 登录失败 req={}", sysUserPO, e);
            view.addAttribute("msg", "服务不可用，请联系管理员");
        }
        return "main/loginPage";
    }
}
