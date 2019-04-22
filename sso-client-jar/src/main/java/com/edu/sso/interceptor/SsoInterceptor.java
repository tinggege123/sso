package com.edu.sso.interceptor;

import com.edu.entity.SsoLoginEntity;
import com.edu.sso.constants.ClientBean;
import com.edu.sso.constants.ClientContants;
import com.edu.utils.FinalData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 单点登录的拦截器
 *
 * @author 王生挺
 * @date 2018年7月17日23:16:20
 */
@Slf4j
@Component
public class SsoInterceptor implements HandlerInterceptor {

    @Resource
    private ClientBean clientBean;

    /**
     * 进入执行方法的拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //session状态判断
        HttpSession session = request.getSession();
        log.info("[sso 拦截器] sessionId=" + session.getId());
        Object attribute = session.getAttribute(FinalData.IS_LOGIN);
        if (attribute != null && (boolean) attribute) {
            return true;
        }
        //跳转进行登录
        String requestType = request.getHeader("x-requested-with");
        //如果是ajax异步调用，进行特殊处理，让浏览器进行重定向
        if ("XMLHttpRequest".equals(requestType)) {
            log.info("[sso 拦截器] ajax请求");
            response.setStatus(302);
            SsoLoginEntity ssoLoginEntity = SsoLoginEntity.builder()
                    .callback(ClientContants.CLIENT_IP_PORT)
                    .toUrl(ClientContants.SERVER_AUTH_URL)
                    .projectName(clientBean.getProjectName())
                    .build();
            //响应客户端
            write(response, ssoLoginEntity);
            return false;
        }
        log.info("[sso 拦截器] 同步请求");
        request.setAttribute(ClientContants.TEMPORARY_URL, request.getRequestURI());
        request.getRequestDispatcher(ClientContants.TO_SERVER_AUTH_URL).forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    }

    /**
     * 将结果返回客户端
     */
    public static void write(HttpServletResponse response, Object o) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        ObjectMapper om = new ObjectMapper();
        out.print(om.writeValueAsString(o));
        out.flush();
        out.close();
    }
}
