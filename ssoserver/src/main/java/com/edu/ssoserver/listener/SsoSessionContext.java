package com.edu.ssoserver.listener;

import com.edu.constants.HttpClientEnum;
import com.edu.ssoserver.constants.ServerContants;
import com.edu.utils.EduResult;
import com.edu.utils.FinalData;
import com.edu.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session的自定义添加
 *
 * @author wst
 * @date 2018/9/22 17:53
 **/
@Slf4j
public class SsoSessionContext {

    private static ConcurrentHashMap mymap = new ConcurrentHashMap();

    public static void addSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static  void delSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static  HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) mymap.get(session_id);
    }

    public static void clearSession(HttpSession session){
        delSession(session);
        session.invalidate();
    }

    public static void clearSession(HttpSession session,String serverToken) {
        //当系统在主页登录，没有登录任何的子系统
        Map<String, String> urlMaps = (Map<String, String>) session.getAttribute(FinalData.MAP_URL_SESSIONID);
        if(urlMaps == null || urlMaps.size() ==0){
            log.info("[sso 退出] 当前没有子系统登录  serverToken={}", serverToken);
            //清除session
            SsoSessionContext.clearSession(session);
            return;
        }

        log.info("[sso 通知子系统退出开始] maps={}",urlMaps.toString());
        //将子系统一个个退出
        for (Map.Entry<String, String> entry : urlMaps.entrySet()) {
            try{
                Map<String,String> maps = new HashMap<>();
                maps.put(FinalData.SSO_TOKEN,serverToken);
                maps.put(FinalData.LOCAL_SESSION_ID,entry.getValue());
                EduResult result = HttpClientUtil.postResultClass(entry.getKey()+"ssoClient/clientLogout", maps);
                //重试机制
                if(!(HttpClientEnum.SUCCESS.code == result.getStatus())){
                    log.info("[sso 通知子系统退出失败]重试" );
                    HttpClientUtil.postResultClass(entry.getKey()+ ServerContants.CLIENT_LOGOUT_URL, maps);
                }
            }catch (Exception e){
                log.error("[sso 通知子系统退出失败] err={}" ,e);
            }
        }
        SsoSessionContext.clearSession(session);
    }
}
