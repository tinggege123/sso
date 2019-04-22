package com.edu.ssoserver.service.impl;

import com.edu.business.persistence.po.LoginHistoryPO;
import com.edu.business.persistence.po.UserPO;
import com.edu.business.persistence.po.UserTokenPO;
import com.edu.constants.HttpClientEnum;
import com.edu.entity.SsoLoginEntity;
import com.edu.ssoserver.bo.AuthBO;
import com.edu.ssoserver.bo.LoginBO;
import com.edu.ssoserver.em.LoginStatusEnum;
import com.edu.ssoserver.listener.SsoSessionContext;
import com.edu.ssoserver.service.AbstractSsoParent;
import com.edu.ssoserver.service.SsoService;
import com.edu.utils.EduResult;
import com.edu.utils.FinalData;
import com.edu.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * session访问接口
 *
 * @author wst
 * @date 2018/11/18 14:34
 **/
@Slf4j
public class SsoSessionServiceImpl extends AbstractSsoParent implements SsoService {

    /**
     * session登录模式下的判断是否已登录
     *
     * @param request        url请求域
     * @param ssoLoginEntity 登录实体
     * @param attr           返回的请求体
     * @return
     */
    @Override
    public AuthBO auth(HttpServletRequest request, SsoLoginEntity ssoLoginEntity, RedirectAttributes attr) {
        AuthBO resultAuthBo = AuthBO.builder().loginStatus(LoginStatusEnum.SESSION_LOGIN.getStatus()).build();
        //尝试获取用户权限信息
        HttpSession session = request.getSession();
        log.info("[sso client请求server] id={}", session.getId());
        Object userAuth = session.getAttribute(FinalData.SSO_TOKEN);
        //session是否存在
        if (userAuth != null) {
            log.info("[sso client请求server] 已登录");
            //1.设置token,2.返回跳转的url,3.当前的sessionId
            attr.addAttribute(FinalData.SSO_TOKEN, session.getAttribute(FinalData.SSO_TOKEN));
            attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
            attr.addAttribute(FinalData.SERVER_SESSION_ID, session.getId());
            resultAuthBo.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
            return resultAuthBo;
        }
        //验证cookie是否合法
        if (this.cookieForLogin(request)) {
            log.info("[sso client请求server] cookie成功登录 id={}", session.getId());
            attr.addAttribute(FinalData.SSO_TOKEN, session.getAttribute(FinalData.SSO_TOKEN));
            attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
            attr.addAttribute(FinalData.SERVER_SESSION_ID, session.getId());
            resultAuthBo.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
            return resultAuthBo;
        }
        //设置返回应用系统的url
        if (ssoLoginEntity != null) {
            session.setAttribute(FinalData.CALL_BACK, ssoLoginEntity);
        }
        return resultAuthBo;
    }

    /**
     * 登录
     *
     * @param resultUserPO 查询数据库后的po
     * @param request      请求域
     * @param response     响应域
     * @param attr         返回体
     * @return 返回的BO
     */
    @Override
    public LoginBO login(UserPO resultUserPO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        LoginBO loginBO = LoginBO.builder().loginStatus(LoginStatusEnum.SESSION_LOGIN.getStatus()).build();
        HttpSession session = request.getSession();
        log.info("[sso登录] session登录 id={}", session.getId());

        //获取token
        String token = IDUtils.getUUID();
        session.setAttribute(FinalData.SSO_TOKEN, token);
        SsoLoginEntity ssoLoginEntity = (SsoLoginEntity) session.getAttribute(FinalData.CALL_BACK);
        resultUserPO.setToken(token);

        //将token添加到数据库，并且添加cookie
        this.addToken(resultUserPO, response);

        //1.设置token,2.返回跳转的url,3.当前的sessionId
        attr.addAttribute(FinalData.SSO_TOKEN, token);
        attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
        attr.addAttribute(FinalData.SERVER_SESSION_ID, session.getId());
        loginBO.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
        return loginBO;
    }

    /**
     * client端请求验证token
     *
     * @param ssoToken        客户端token
     * @param serverSessionId 服务器的sessionId,当登录方式为database,redis时不需要该参数
     * @param request         请求域
     * @return 返回BO
     */
    @Override
    public EduResult getStatusByToken(String ssoToken, String serverSessionId, HttpServletRequest request) {
        //获取的是登陆的session
        HttpSession session = SsoSessionContext.getSession(serverSessionId);

        //获取token
        String nowToken = (String) session.getAttribute(FinalData.SSO_TOKEN);
        String localSessionId = request.getParameter(FinalData.LOCAL_SESSION_ID);
        String clientUrl = request.getParameter(FinalData.CLIENT_URL);

        //验证参数是否正确
        if (StringUtils.isBlank(nowToken) || StringUtils.isBlank(localSessionId) || StringUtils.isBlank(clientUrl)) {
            log.error("[sso 验证token] 参数验证失败 ");
            EduResult.build(HttpClientEnum.PARAM_ERR.code, HttpClientEnum.PARAM_ERR.msg);
        }
        try {
            if (nowToken.equals(ssoToken)) {
                log.info("[sso 验证token] 成功 token={}", nowToken);
                //保存url和sessionId的map
                Map<String, String> urlMap = (Map<String, String>) session.getAttribute(FinalData.MAP_URL_SESSIONID);
                if (urlMap == null) {
                    urlMap = new HashMap<>();
                }
                urlMap.put(clientUrl, localSessionId);
                session.setAttribute(FinalData.MAP_URL_SESSIONID, urlMap);
                return EduResult.build(200, "token验证成功");
            }
        } catch (Exception e) {
            log.info("[sso 验证token]验证失败", e);
        }
        return EduResult.build(500, "token验证失败");
    }

    /**
     * client端请求退出
     *
     * @param serverSessionId 服务器的sessionId,当登录方式为database,redis时不需要该参数
     * @param ssoToken
     * @return 返回
     */
    @Override
    public EduResult logout(String serverSessionId, String ssoToken) {
        log.info("[sso 退出] 开始");

        //验证参数
        if (StringUtils.isBlank(serverSessionId) || StringUtils.isBlank(ssoToken)) {
            log.info("[sso 退出] 参数验证失败 serverSessionId={} ssoToken={}", serverSessionId, ssoToken);
            return EduResult.build(HttpClientEnum.PARAM_ERR.code, HttpClientEnum.PARAM_ERR.msg);
        }

        //获取session
        HttpSession session = SsoSessionContext.getSession(serverSessionId);
        if (session == null) {
            log.info("[sso 退出] session不存在 serverSessionId={} ssoToken={}", serverSessionId, ssoToken);
            return EduResult.build(HttpClientEnum.SESSION_NO.code, HttpClientEnum.SESSION_NO.msg);
        }

        //验证token是否一致
        String serverToken = (String) session.getAttribute(FinalData.SSO_TOKEN);
        if (!ssoToken.equals(serverToken)) {
            log.info("[sso 退出] token验证失败 clientToken={} serverToken={}", ssoToken, serverToken);
            return EduResult.build(HttpClientEnum.TOKEN_ERR.code, HttpClientEnum.TOKEN_ERR.msg);
        }
        //清楚数据库中的token
        clearTokenFromDataBase(serverToken);

        //清除session信息
        SsoSessionContext.clearSession(session, serverToken);

        log.info("[sso 退出] 成功 clientToken={}", ssoToken);

        return EduResult.ok();
    }


    /**
     * 单点登录退出时清除数据
     *
     * @param serverToken
     */
    private void clearTokenFromDataBase(String serverToken) {
        if (!cookieStatus) {
            log.info("[sso 退出] cookie验证未开启");
        }
        try {
            log.info("[sso 退出] 清除数据库的token 开始");
            //更新token
            loginHistoryService.delete(LoginHistoryPO.builder().token(serverToken).build());
            log.info("[sso 退出] 清除数据库的token 成功");
        } catch (Exception e) {
            log.error("[sso 退出] 清楚数据库失败 ", e);
        }
    }

    @Override
    protected void logoutUserAndSaveNewUser(UserPO resultUserPO) {
        UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder()
                .token(resultUserPO.getToken()).build());
        if (userToken != null) {
            HttpSession session = SsoSessionContext.getSession(userToken.getServerSessionId());
            //清除session信息
            SsoSessionContext.clearSession(session, userToken.getToken());
        }
        userTokenService.save(UserTokenPO.builder()
                .username(resultUserPO.getUsername()).token(resultUserPO.getToken()).build());
    }
}
