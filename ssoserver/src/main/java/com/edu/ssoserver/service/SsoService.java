package com.edu.ssoserver.service;

import com.edu.entity.SsoLoginEntity;
import com.edu.business.persistence.po.UserPO;
import com.edu.ssoserver.bo.AuthBO;
import com.edu.ssoserver.bo.LoginBO;
import com.edu.utils.EduResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单点登录接口
 *
 * @author wst
 * @date 2018/11/18 14:34
 **/
public interface SsoService {

    /**
     * 登录模式下的判断是否已登录
     *
     * @param request        url请求域
     * @param ssoLoginEntity 登录实体
     * @param attr           返回的请求体
     * @return 返回的BO
     */
    AuthBO auth(HttpServletRequest request, SsoLoginEntity ssoLoginEntity, RedirectAttributes attr);

    /**
     * 登录
     *
     * @param userPO   查询数据库后的po
     * @param request  请求域
     * @param response 响应域
     * @param attr     返回体
     * @return 返回的BO
     */
    LoginBO login(UserPO userPO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr);

    /**
     * client端请求验证token
     *
     * @param ssoToken        客户端token
     * @param serverSessionId 服务器的sessionId,当登录方式为database,redis时不需要该参数
     * @param request         请求域
     * @return 返回
     */
    EduResult getStatusByToken(String ssoToken, String serverSessionId, HttpServletRequest request);

    /**
     * client端请求退出
     *
     * @param serverSessionId 服务器的sessionId,当登录方式为database,redis时不需要该参数
     * @param ssoToken
     * @return 返回
     */
    EduResult logout(String serverSessionId, String ssoToken);
}
