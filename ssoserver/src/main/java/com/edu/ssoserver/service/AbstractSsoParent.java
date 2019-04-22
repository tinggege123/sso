package com.edu.ssoserver.service;

import com.edu.business.persistence.po.UserPO;
import com.edu.business.persistence.po.UserTokenPO;
import com.edu.business.persistence.service.LoginHistoryService;
import com.edu.business.persistence.service.UserService;
import com.edu.business.persistence.service.UserTokenService;
import com.edu.business.service.LogService;
import com.edu.constants.HttpClientEnum;
import com.edu.entity.SsoLoginEntity;
import com.edu.ssoserver.constants.ServerContants;
import com.edu.ssoserver.em.MultiUserLoginEnum;
import com.edu.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * desc
 *
 * @author wst
 * @date 2018/11/24 14:01
 **/
@Slf4j
public abstract class AbstractSsoParent {

    @Value("${sso.cookieStatus}")
    public boolean cookieStatus;

    @Value("${sso.cookieOutOfDate}")
    public int cookieOutOfDate;

    @Value("${sso.multiUserLogin}")
    public int multiUserLogin;

    @Autowired
    public UserService userService;

    @Autowired
    public LoginHistoryService loginHistoryService;

    @Autowired
    public UserTokenService userTokenService;

    @Autowired
    public ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    public LogService logService;

    /**
     * token写入数据库和cookie
     *
     * @param resultUserPO
     * @param response
     */
    public void addToken(UserPO resultUserPO, HttpServletResponse response) {
        if (!cookieStatus) {
            log.info("[sso登录] cookie登录未开启");
            return;
        }
        log.info("[sso登录] cookie写入开始 username={}", resultUserPO.getUsername());

        //更新token
//        userService.updateTokenById(resultUserPO);
        if (MultiUserLoginEnum.MULTI.getCode() == multiUserLogin) {
            log.info("[sso登录] 允许多用户进行登录 username={}", resultUserPO.getUsername());
            userTokenService.save(UserTokenPO.builder()
                    .username(resultUserPO.getUsername()).token(resultUserPO.getToken()).build());
        } else {
            this.logoutUserAndSaveNewUser(resultUserPO);
        }

        //添加token到cookie
        response.addCookie(new Cookie(FinalData.TGT, resultUserPO.getToken()));

        log.info("[sso登录] cookie写入成功 username={}", resultUserPO.getUsername());
    }

    /**
     * 单终端登录，如果已经有用户登录，那就进行退出之前的。
     *
     * @param resultUserPO
     */
    protected abstract void logoutUserAndSaveNewUser(UserPO resultUserPO);

    public void notifyClientLogout(Map<String, String> urlMaps, String token) {
        log.info("[sso 通知子系统退出开始] maps={}", urlMaps.toString());
        //将子系统一个个退出
        for (Map.Entry<String, String> entry : urlMaps.entrySet()) {
            try {
                Map<String, String> maps = new HashMap<>();
                maps.put(FinalData.SSO_TOKEN, token);
                maps.put(FinalData.LOCAL_SESSION_ID, entry.getValue());
                EduResult result = HttpClientUtil.postResultClass(entry.getKey() + "ssoClient/clientLogout", maps);
                //重试机制
                if (!(HttpClientEnum.SUCCESS.code == result.getStatus())) {
                    log.info("[sso 通知子系统退出失败]重试");
                    HttpClientUtil.postResultClass(entry.getKey() + ServerContants.CLIENT_LOGOUT_URL, maps);
                }
            } catch (Exception e) {
                log.error("[sso 通知子系统退出失败] err={}", e);
            }
        }
    }

    /**
     * 从request中获取cookie
     *
     * @param request
     */
    public SsoLoginEntity getCookieFromRequest(HttpServletRequest request) {
        SsoLoginEntity ssoLoginEntity = null;
        try {
            Map<String, String> cookies = CookieUtil.getCookies(request);
            String callbackData = cookies.get(ServerContants.CALLBACK_DATA);
            if (StringUtils.isEmpty(callbackData)) {
                log.info("[读取返回子系统cookie] 不存在,无法返回子系统");
                return ssoLoginEntity;
            }
            String reallyCallbackStr = BaseUtil.decryptBASE64(callbackData);
            ObjectMapper objectMapper = new ObjectMapper();
            ssoLoginEntity = objectMapper.readValue(reallyCallbackStr, SsoLoginEntity.class);
        } catch (IOException e) {
            log.error("[读取返回子系统cookie] 解析json字符串失败", e);
        }
        return ssoLoginEntity;
    }


    /**
     * 检验cookie是否合法
     *
     * @param request
     * @return
     */
    public boolean cookieForLogin(HttpServletRequest request) {
        if (!cookieStatus) {
            log.info("[cookie 验证] cookie登录未开启");
            return false;
        }
        HttpSession session = request.getSession();
        try {
            //cookie解析
            Map<String, String> cookieMap = CookieUtil.getCookies(request);
            String tgt = cookieMap.get(FinalData.TGT);
            if (StringUtils.isBlank(tgt)) {
                log.info("[cookie 验证] 失败-cookie的TGT为空");
                return false;
            }
            UserTokenPO userToken = userTokenService.getUserToken(UserTokenPO.builder().token(tgt).build());
            if (null == userToken) {
                log.info("[cookie 验证] 数据库未找到 tgt={}", tgt);
                return false;
            }
            //添加时间
            Date loginDate = userToken.getCreateTime();
            long time = loginDate.getTime();
            time += cookieOutOfDate * 24 * 60 * 60 * 1000;
            loginDate = new Date(time);

            //比对时间，是否过期
            if (new Date().compareTo(loginDate) == 1) {
                log.info("[cookie 验证] 登录时间已经到达重新登录的时间 cookieOutOfDate={} nowDate={}", cookieOutOfDate, new Date());
                return false;
            }
            session.setAttribute(FinalData.SSO_TOKEN, tgt);
        } catch (Exception e) {
            log.error("[cookie 验证] 异常", e);
            return false;
        }
        return true;
    }
}
