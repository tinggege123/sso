package com.edu.sso.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 描述
 *
 * @author wst
 * @date 2018/9/22 17:53
 **/
@Slf4j
public class SsoSessionContext {
    private static HashMap mymap = new HashMap();

    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String sessionId) {
        if (sessionId == null){
            return null;
        }
        return (HttpSession) mymap.get(sessionId);
    }
    public static synchronized void delSessioByName(String data) {
        if(StringUtils.isEmpty(data)){
            return;
        }

    }
}
