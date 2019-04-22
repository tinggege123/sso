package com.edu.ssoserver.listener;

import com.edu.utils.FinalData;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 删除session
 *
 * @author wst
 * @date 2018/9/22 17:54

 **/
@Slf4j
public class SsoSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SsoSessionContext.addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        try{
            log.info("[session管理中心] destroy 开始 req=[]",session);
            String serverToken = (String)session.getAttribute(FinalData.SSO_TOKEN);
            SsoSessionContext.clearSession(session,serverToken);
        }catch (Exception e){
            log.error("[session管理中心] destroy 失败",e);
        }
        log.info("[session 管理中心] destroy 成功");
        SsoSessionContext.delSession(session);
    }
}