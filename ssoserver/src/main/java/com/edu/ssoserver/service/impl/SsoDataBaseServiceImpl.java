package com.edu.ssoserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.edu.business.persistence.po.LogHistoryPO;
import com.edu.business.persistence.po.LoginHistoryPO;
import com.edu.business.persistence.po.UserPO;
import com.edu.business.persistence.po.UserTokenPO;
import com.edu.constants.HttpClientEnum;
import com.edu.entity.SsoLoginEntity;
import com.edu.ssoserver.bo.AuthBO;
import com.edu.ssoserver.bo.LoginBO;
import com.edu.ssoserver.em.LoginStatusEnum;
import com.edu.ssoserver.service.AbstractSsoParent;
import com.edu.ssoserver.service.SsoService;
import com.edu.utils.CookieUtil;
import com.edu.utils.EduResult;
import com.edu.utils.FinalData;
import com.edu.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库访问service
 *
 * @author wst
 * @date 2018/11/18 15:42
 **/
@Slf4j
public class SsoDataBaseServiceImpl extends AbstractSsoParent implements SsoService {

    /**
     * 登录模式下的判断是否已登录
     *
     * @param request        url请求域
     * @param ssoLoginEntity 登录实体
     * @param attr           返回的请求体
     * @return
     */
    @Override
    public AuthBO auth(HttpServletRequest request, SsoLoginEntity ssoLoginEntity, RedirectAttributes attr) {
        AuthBO resultAuthBo = AuthBO.builder().loginStatus(LoginStatusEnum.DATABASE_LOGIN.getStatus()).build();
        //获取cookie
        Map<String, String> cookieMap = CookieUtil.getCookies(request);

        //验证cookie是否合法
        if (this.cookieForLogin(cookieMap)) {
            String token = cookieMap.get(FinalData.TGT);
            log.info("[sso client请求server] cookie成功登录 token={}", token);
            attr.addAttribute(FinalData.SSO_TOKEN, token);
            attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
            //在数据库登录方式中，serverSessionId不需要
            attr.addAttribute(FinalData.SERVER_SESSION_ID, IDUtils.getShortID());
            resultAuthBo.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
            logService.saveLog(LogHistoryPO.builder().opearte("请求登录中心验证").username(token)
                    .content("令牌为" + token + "请求登录中心进行登录，callback是" + ssoLoginEntity.getLoginSuccessHtml()).build());
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

        SsoLoginEntity ssoLoginEntity = this.getCookieFromRequest(request);

        //获取token
        String token = IDUtils.getUUID();
        resultUserPO.setToken(token);


        //将token添加到数据库，并且添加cookie
        this.addToken(resultUserPO, response);
        if (ssoLoginEntity == null) {
            return loginBO;
        }

        //1.设置token,2.返回跳转的url,3.当前的sessionId
        attr.addAttribute(FinalData.SSO_TOKEN, token);
        attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
        attr.addAttribute(FinalData.SERVER_SESSION_ID, IDUtils.getUUID().substring(8));
        loginBO.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
        logService.saveLog(LogHistoryPO.builder().opearte("进行登录").username(token)
                .content("令牌为" + token + "进行登录，callback是" + ssoLoginEntity.getLoginSuccessHtml()).build());
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
        String localSessionId = request.getParameter(FinalData.LOCAL_SESSION_ID);
        String clientUrl = request.getParameter(FinalData.CLIENT_URL);
        String projectName = request.getParameter(FinalData.PROJECT_NAME);
        if (StringUtils.isEmpty(ssoToken)
                || StringUtils.isEmpty(localSessionId) || StringUtils.isEmpty(clientUrl)) {
            log.warn("[sso 验证token] token为空，参数验证失败 ");
            return EduResult.build(500, "token为空");
        }
        try {
            UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder().token(ssoToken).build());

            if (userToken == null) {
                log.warn("[sso 验证token] 用户未登录 token={}", ssoToken);
                return EduResult.build(500, "用户未登录");
            }
            log.info("[sso 验证token] 用户已登录 tokenBo={}", JSON.toJSONString(userToken));
            //开启线程保存数据
            threadPoolTaskExecutor.submit(() -> saveLoginHistoryData(ssoToken, clientUrl, localSessionId, projectName));

            logService.saveLog(LogHistoryPO.builder().opearte("进行验证TOKEN").username(ssoToken)
                    .content("令牌为" + ssoToken + "进行验证TOKEN，子系统的sessionId是" + localSessionId).build());

            return EduResult.ok();
        } catch (Exception e) {
            log.error("[sso 验证token] 验证失败", e);
            return EduResult.build(500, "服务器异常");
        }
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
        //校验是否为空
        if (StringUtils.isEmpty(ssoToken)) {
            log.warn("[sso 退出] token为空，参数验证失败");
            return EduResult.build(HttpClientEnum.PARAM_ERR.code, HttpClientEnum.PARAM_ERR.msg);
        }
        //校验是否登录
        UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder().token(ssoToken).build());
        if (userToken == null) {
            log.warn("[sso 退出] 用户当前未登录 ssoToken={}", ssoToken);
            return EduResult.build(HttpClientEnum.SELF_BUILD.code, "用户未登录");
        }
        List<LoginHistoryPO> loginHistoryPOS = loginHistoryService.listByCondition(LoginHistoryPO.builder()
                .token(userToken.getToken()).build());
        Map<String, String> urlMaps = new HashMap<>();
        for (LoginHistoryPO item : loginHistoryPOS) {
            urlMaps.put(item.getIpPort(), item.getSessionId());
        }
        this.notifyClientLogout(urlMaps, userToken.getToken());
        userTokenService.delete(UserTokenPO.builder()
                .username(userToken.getUsername()).build());
        loginHistoryService.delete(LoginHistoryPO.builder().token(userToken.getToken()).build());

        logService.saveLog(LogHistoryPO.builder().opearte("退出").username(userToken.getUsername())
                .content("令牌为" + ssoToken + "进行退出，登录中心系统的sessionId是" + serverSessionId).build());

        return EduResult.ok();
    }

    /**
     * 检验cookie是否合法
     *
     * @param cookieMap
     * @return
     */
    private boolean cookieForLogin(Map<String, String> cookieMap) {
        if (!cookieStatus) {
            log.info("[cookie 验证] cookie登录未开启");
            return false;
        }
        try {
            //cookie解析
            String tgt = cookieMap.get(FinalData.TGT);
            if (StringUtils.isBlank(tgt)) {
                log.info("[cookie 验证] 失败-cookie的TGT为空");
                return false;
            }
            LoginHistoryPO loginHistoryPO = loginHistoryService.queryByCondition(LoginHistoryPO.builder().token(tgt).build());
            if (null == loginHistoryPO) {
                log.info("[cookie 验证] 数据库未找到 tgt={}", tgt);
                return false;
            }
            //添加时间
            Date loginDate = loginHistoryPO.getCreateTime();
            long time = loginDate.getTime();
            time += cookieOutOfDate * 24 * 60 * 60 * 1000;
            loginDate = new Date(time);

            //比对时间，是否过期
            if (new Date().compareTo(loginDate) == 1) {
                log.info("[cookie 验证] 登录时间已经到达重新登录的时间 cookieOutOfDate={} nowDate={}", cookieOutOfDate, new Date());
                return false;
            }
        } catch (Exception e) {
            log.error("[cookie 验证] 异常", e);
            return false;
        }
        return true;
    }

    @Override
    public void logoutUserAndSaveNewUser(UserPO resultUserPO) {
        try {
            UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder()
                    .username(resultUserPO.getUsername()).build());
            if (userToken != null) {

                List<LoginHistoryPO> loginHistoryPOS = loginHistoryService.listByCondition(LoginHistoryPO.builder()
                        .token(userToken.getToken()).build());
                Map<String, String> urlMaps = new HashMap<>();
                for (LoginHistoryPO item : loginHistoryPOS) {
                    urlMaps.put(item.getIpPort(), item.getSessionId());
                }
                this.notifyClientLogout(urlMaps, userToken.getToken());
                userTokenService.delete(UserTokenPO.builder()
                        .username(resultUserPO.getUsername()).build());
                loginHistoryService.delete(LoginHistoryPO.builder().token(userToken.getToken()).build());
            }
            userTokenService.save(UserTokenPO.builder()
                    .username(resultUserPO.getUsername()).token(resultUserPO.getToken()).build());
        } catch (Exception e) {
            log.error("[sso登录] 保存token数据表失败 username={}", resultUserPO.getUsername(), e);
        }
    }

    /**
     * 保存登录历史记录
     *
     * @param ssoToken       令牌
     * @param clientUrl      子系统client
     * @param localSessionId 子系统的session
     */
    public void saveLoginHistoryData(String ssoToken, String clientUrl, String localSessionId, String projectName) {
        LoginHistoryPO queryPO = LoginHistoryPO.builder().token(ssoToken).ipPort(clientUrl).build();
        LoginHistoryPO resultPO = loginHistoryService.queryByCondition(queryPO);
        if (resultPO == null) {
            UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder().token(ssoToken).build());
            queryPO.setSessionId(localSessionId);
            queryPO.setProjectName(projectName);
            queryPO.setUsername(userToken.getUsername());
            loginHistoryService.save(queryPO);
        } else {
            resultPO.setSessionId(localSessionId);
            loginHistoryService.updateById(resultPO);
        }
    }
}
