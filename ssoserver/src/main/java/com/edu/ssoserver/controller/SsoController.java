package com.edu.ssoserver.controller;

import com.alibaba.fastjson.JSON;
import com.edu.business.persistence.po.UserPO;
import com.edu.business.persistence.service.UserService;
import com.edu.entity.SsoLoginEntity;
import com.edu.ssoserver.bo.AuthBO;
import com.edu.ssoserver.bo.LoginBO;
import com.edu.ssoserver.constants.ServerContants;
import com.edu.ssoserver.em.LoginStatusEnum;
import com.edu.ssoserver.service.SsoService;
import com.edu.ssoserver.util.MiUtils;
import com.edu.utils.BaseUtil;
import com.edu.utils.EduResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sso操作类
 *
 * @author 王生挺
 * @version 1.0
 * @date 2018年7月14日 16:26:25
 */
@Slf4j
@Controller
@RequestMapping("ssoServer")
public class SsoController {

    @Value("${sso.cookieStatus}")
    private boolean cookieStatus;

    @Value("${sso.cookieOutOfDate}")
    private int cookieOutOfDate;

    @Autowired
    private UserService userService;

    @Resource
    private SsoService ssoService;

    /**
     * 客户端请求服务器验证是否已登录
     *
     * @param request        请求域
     * @param ssoLoginEntity 客户端上传的实体信息
     * @param attr           返回的实体信息
     * @return
     * @throws IOException
     */
    @RequestMapping("/auth")
    public String syncSessionSrv(HttpServletRequest request, SsoLoginEntity ssoLoginEntity, RedirectAttributes attr, HttpServletResponse response) throws IOException {
        log.info("[sso 检验是否登录] 开始");
        AuthBO auth = ssoService.auth(request, ssoLoginEntity, attr);
        log.info("[sso 检验是否登录] 结束 resp={}", auth);
        if (auth == null || StringUtils.isBlank(auth.getRedirectUrl())) {
            //设置返回的cookie
            if (auth.getLoginStatus() != LoginStatusEnum.SESSION_LOGIN.getStatus()) {
                String key = BaseUtil.encryptBASE64(JSON.toJSONString(ssoLoginEntity));
                response.addCookie(new Cookie(ServerContants.CALLBACK_DATA, key));
            }
            return ServerContants.INDEX;
        }
        return auth.getRedirectUrl();
    }


    @RequestMapping("/login")
    public String userLogin(UserPO userPO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        try {
            //数据加密
            String password = userPO.getPassword();
            userPO.setPassword(MiUtils.getMd5(password));
            //登录
            UserPO resultUserPO = userService.selectDataByUsernameAnd(userPO);
            if (resultUserPO == null) {
                log.warn("[sso登录] 账号或密码错误 username={}", userPO.getUsername());
                request.setAttribute("msg", "账号或密码错误");
                return ServerContants.INDEX;
            }

            //登录成功，根据不同的登录方式进行处理
            LoginBO login = ssoService.login(resultUserPO, request, response, attr);
            if (login == null || StringUtils.isEmpty(login.getRedirectUrl())) {
                log.info("[sso登录] 登录成功，但没有返回子系统的url没有，跳转主页面");
                return ServerContants.MAIN;
            }
            log.info("[sso登录] 成功,返回子系统 resp={}", login);

            //如果返回了值就是跳转回子系统的url
            return login.getRedirectUrl();

        } catch (Exception e) {
            log.error("[sso登录] 失败", e);
            request.setAttribute("msg", "服务器发生错误，请联系管理员");
        }
        return ServerContants.INDEX;
    }


    /**
     * client端验证token,并保存client端的sessionId
     *
     * @param ssoToken
     * @param serverSessionId
     * @return
     */
    @RequestMapping("/getStatusByToken")
    @ResponseBody
    public EduResult getStatusByToken(String ssoToken, String serverSessionId, HttpServletRequest request) {
        try {
            log.info("[sso 验证token] 开始 token={}", ssoToken);
            EduResult statusByToken = ssoService.getStatusByToken(ssoToken, serverSessionId, request);
            log.info("[sso 验证token] 结束 resp={}", statusByToken);
            return statusByToken;
        } catch (Exception e) {
            log.info("[sso 验证token] 失败", e);
            return EduResult.error();
        }
    }


    @RequestMapping("/logout")
    @ResponseBody
    public EduResult logout(String serverSessionId, String ssoToken) {
        try {
            log.info("[sso 退出] 开始 token={}", ssoToken);
            EduResult logout = ssoService.logout(serverSessionId, ssoToken);
            log.info("[sso 退出] 结束 token={} resp={}", ssoToken, logout);
            return logout;
        } catch (Exception e) {
            log.info("[sso 退出] 失败", e);
            return EduResult.error();
        }
    }

    @RequestMapping("/register")
    @ResponseBody
    public EduResult register(UserPO userPO) {
        try {
            log.info("[sso 注册] 开始 req={}", JSON.toJSON(userPO));
            if (userPO == null || StringUtils.isBlank(userPO.getUsername()) || StringUtils.isBlank(userPO.getPassword())) {
                log.error("[sso 注册] 参数校验失败 req={}", JSON.toJSON(userPO));
                return EduResult.build(502, "参数校验失败");
            }
            return userService.register(userPO);
        } catch (Exception e) {
            log.error("[sso 注册] 失败 req={}", JSON.toJSON(userPO), e);
            return EduResult.error("注册失败");
        }
    }

    @RequestMapping("/index")
    public String toIndex() {
        log.info("[sso SsoEncrypt]");
        log.info("[sso] cookieStatus={}", cookieStatus);
        return "index";
    }

}
