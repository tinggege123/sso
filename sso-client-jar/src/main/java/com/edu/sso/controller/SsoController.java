package com.edu.sso.controller;

import com.edu.constants.HttpClientEnum;
import com.edu.entity.SsoLoginEntity;
import com.edu.sso.constants.ClientBean;
import com.edu.sso.constants.ClientContants;
import com.edu.sso.listener.SsoSessionContext;
import com.edu.sso.service.SsoHttpToServerService;
import com.edu.utils.EduResult;
import com.edu.utils.FinalData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 单点登录的发送和返回类
 *
 * @author 王生挺
 * @date 2018年7月22日17:35:58
 */
@Slf4j
@Controller
@RequestMapping("/ssoClient")
public class SsoController {

    @Autowired
    private SsoHttpToServerService ssoHttpToServerService;

    @Resource
    private ClientBean clientBean;

//    private static String sync_url = "http://localhost:8081/";

    /**
     * 发送请求到注册中心
     */
    @RequestMapping("/sync")
    @ResponseBody
    public EduResult syncSession(HttpServletResponse response) {
        response.setStatus(302);
        SsoLoginEntity ssoLoginEntity = SsoLoginEntity.builder()
                .callback(ClientContants.CLIENT_IP_PORT)
                .toUrl(ClientContants.SERVER_AUTH_URL)
                .build();
        return EduResult.build(302, "跳转登录", ssoLoginEntity);
    }

    @RequestMapping("/callback")
    public String syncResult(HttpServletRequest request, RedirectAttributes attr, HttpServletResponse response){
        try {
            HttpSession session = request.getSession();
            String token = request.getParameter(FinalData.SSO_TOKEN);
            String serverSessionId = request.getParameter(FinalData.SERVER_SESSION_ID);
            log.info("[sso回调] 验证的id={}", session.getId());
            //请求server,验证token是否成功
            Map<String, String> map = new HashMap<String, String>();
            map.put(FinalData.SSO_TOKEN, token);
            map.put(FinalData.LOCAL_SESSION_ID, session.getId());
            map.put(FinalData.SERVER_SESSION_ID, serverSessionId);
            map.put(FinalData.PROJECT_NAME,clientBean.getProjectName());
            map.put(FinalData.CLIENT_URL, ClientContants.CLIENT_IP_PORT);
            EduResult tokenStatus = ssoHttpToServerService.getStatusByToken(ClientContants.SERVER_IP_PORT + ClientContants.IDENTITY_TOKEN_URL, map);

            //验证成功，返回之前的url
            if (HttpClientEnum.SUCCESS.code == tokenStatus.getStatus()) {
                log.info("[sso回调] 成功 req={}", tokenStatus.getMsg());
                session.setAttribute(FinalData.IS_LOGIN, true);
                session.setAttribute(FinalData.SSO_TOKEN, token);
                session.setAttribute(FinalData.SERVER_SESSION_ID, serverSessionId);
                String resultUrl = request.getParameter(FinalData.CALL_BACK);
                return "redirect:" + resultUrl;
            }

        } catch (Exception e) {
            log.info("[sso回调] 失败 err={}", e);
            return "redirect:auth/sync.do";
        }
        return "redirect:";
    }

    @RequestMapping("logout")
    @ResponseBody
    public EduResult logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        log.info("[sso client 退出] id={}", session.getId());
        String serverSessionId = (String) session.getAttribute(FinalData.SERVER_SESSION_ID);
        if (StringUtils.isBlank(serverSessionId)) {
            log.info("[sso client 退出] 服务器sessionId不存在 ");
            return EduResult.error();
        }
        String token = (String) session.getAttribute(FinalData.SSO_TOKEN);
        Map<String, String> httpMaps = new HashMap<String, String>();
        httpMaps.put(FinalData.SERVER_SESSION_ID, serverSessionId);
        httpMaps.put(FinalData.SSO_TOKEN, token);
        EduResult tokenStatus = ssoHttpToServerService.getStatusByToken(ClientContants.SERVER_IP_PORT + ClientContants.LOGIN_OUT_URL, httpMaps);
        if (HttpClientEnum.SUCCESS.code == tokenStatus.getStatus()) {
            log.info("[sso 退出] 成功 serverSessionId={}", serverSessionId);
            return EduResult.ok();
        }
        return EduResult.error();
    }

    /**
     * sessionserver通知该client,client清除session
     *
     * @param ssoToken
     * @param localSessionId
     * @return
     */
    @RequestMapping("clientLogout")
    @ResponseBody
    public EduResult clientLogout(String ssoToken, String localSessionId) {
        log.info("[sso server 通知 client 退出] 开始 ssoToken={} localSessionId={}", ssoToken, localSessionId);
        //开始退出
        if (StringUtils.isBlank(ssoToken) || StringUtils.isBlank(localSessionId)) {
            log.info("[sso client 退出] 参数验证失败 ssoToken={} localSessionId={}", ssoToken, localSessionId);
            return EduResult.build(HttpClientEnum.PARAM_ERR.code, HttpClientEnum.PARAM_ERR.msg);
        }

        //验证session
        HttpSession session = SsoSessionContext.getSession(localSessionId);
        if (session == null) {
            log.info("[sso client 退出] session不存在 ssoToken={} localSessionId={}", ssoToken, localSessionId);
            return EduResult.build(HttpClientEnum.SESSION_NO.code, HttpClientEnum.SESSION_NO.msg);
        }

        //验证token是否一致
        String clientToken = (String) session.getAttribute(FinalData.SSO_TOKEN);
        if (!ssoToken.equals(clientToken)) {
            log.info("[sso client 退出] token验证失败 ssoToken={} localSessionId={}", ssoToken, localSessionId);
            return EduResult.build(HttpClientEnum.TOKEN_ERR.code, HttpClientEnum.TOKEN_ERR.msg);
        }

        //验证合法，session清除
        session.invalidate();
        log.info("[sso client 退出] session清楚成功 ssoToken={} localSessionId={}", ssoToken, localSessionId);
        return EduResult.ok();
    }

    @RequestMapping("toServerAuth")
    public String toServerAuth(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        String temporaryUrl = (String)request.getAttribute(ClientContants.TEMPORARY_URL);
        if(StringUtils.isNotBlank(temporaryUrl)){
            temporaryUrl = temporaryUrl.substring(1);
        }
        String requestURI = ClientContants.CLIENT_IP_PORT + temporaryUrl;
        attr.addAttribute("callback", ClientContants.CLIENT_IP_PORT);
        attr.addAttribute("toUrl", ClientContants.SERVER_AUTH_URL);
        attr.addAttribute("loginSuccessHtml", requestURI);
        attr.addAttribute("projectName",clientBean.getProjectName());
        return "redirect:" + ClientContants.SERVER_AUTH_URL;
    }

    @RequestMapping("/index")
    public String toIndex() {
        log.info("[sso SsoEncrypt]");
        return "index";
    }

}
