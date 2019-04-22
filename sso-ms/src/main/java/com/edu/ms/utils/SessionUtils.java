package com.edu.ms.utils;

import com.edu.ms.common.contants.LoginContants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 服务器session操作类
 *
 * @author wst
 * @date 2019/1/13 17:44
 **/
public class SessionUtils {

    private final static String YES = "yes";

    /**
     * 获取登录状态
     *
     * @return
     */
    public static boolean getLoginStatus() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession();
        if (YES.equals(session.getAttribute((LoginContants.SAVE_SESSION)))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 设置登录状态
     */
    public static void setLoginStatus() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession();
        session.setAttribute(LoginContants.SAVE_SESSION, YES);
    }
}
