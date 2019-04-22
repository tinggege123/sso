package com.edu.ssoserver.service.impl;

import com.edu.business.persistence.po.UserPO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * redis单点登录
 *
 * @author wst
 * @date 2018/11/24 09:44
 **/
@Slf4j
public class SsoRedisServiceImpl extends AbstractSsoParent implements SsoService {

    @Override
    public AuthBO auth(HttpServletRequest request, SsoLoginEntity ssoLoginEntity, RedirectAttributes attr) {
        AuthBO resultAuthBo = AuthBO.builder().loginStatus(LoginStatusEnum.REDIS_LOGIN.getStatus()).build();
        //获取cookie
        Map<String, String> cookieMap = CookieUtil.getCookies(request);
        //从用token去redis获取数据

        //如果未获取到去数据库获取
        //1:判断是否允许一处地方进行登录,或者多个地方登录
        //2:
        return null;
    }

    @Override
    public LoginBO login(UserPO userPO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        LoginBO loginBO = LoginBO.builder().loginStatus(LoginStatusEnum.SESSION_LOGIN.getStatus()).build();

        SsoLoginEntity ssoLoginEntity = this.getCookieFromRequest(request);

        //获取token
        String token = IDUtils.getUUID();
        userPO.setToken(token);
        //将token添加到数据库，并且添加cookie
        this.addToken(userPO, response);
        if(ssoLoginEntity == null){
            return loginBO;
        }

        //1.设置token,2.返回跳转的url,3.当前的sessionId
        attr.addAttribute(FinalData.SSO_TOKEN, token);
        attr.addAttribute(FinalData.CALL_BACK, ssoLoginEntity.getLoginSuccessHtml());
        attr.addAttribute(FinalData.SERVER_SESSION_ID, IDUtils.getUUID().substring(8));
        loginBO.setRedirectUrl("redirect:" + ssoLoginEntity.getCallback() + "/ssoClient/callback");
        return loginBO;
    }

    @Override
    public EduResult getStatusByToken(String ssoToken, String serverSessionId, HttpServletRequest request) {
        //判断是否是允许用户在多个位置登录，
        //1.允许
            //1.1将token作为key
        //2.不允许
            //2.1将用户名作为key
        //先去redis获取，没有去库里拿
        //雷同之前的
        return null;
    }

    @Override
    public EduResult logout(String serverSessionId, String ssoToken) {
        //去redis获取，如果拿到了就清楚并且清除数据库
        return null;
    }


    @Override
    protected void logoutUserAndSaveNewUser(UserPO resultUserPO) {

    }
}
