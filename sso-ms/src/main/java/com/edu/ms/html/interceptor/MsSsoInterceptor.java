package com.edu.ms.html.interceptor;

import com.edu.ms.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单点登录的拦截器
 *
 * @author 王生挺
 * @date 2018年7月17日23:16:20
 */
@Slf4j
@Component
public class MsSsoInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PAGE = "loginPage";

    /**
     * 进入执行方法的拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        boolean loginStatus = SessionUtils.getLoginStatus();
        if (!loginStatus) {
            log.warn("[sso后台请求] 用户未登录");
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }

}
