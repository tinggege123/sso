package com.edu.ssoserver.constants;

import com.edu.utils.FinalData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * server常量
 *
 * @author wst
 * @date 2018/9/23 14:19
 **/
@Component
@PropertySource("classpath:application.properties")
public class ServerContants {

    @Value("${server.port}")
    private static Integer port;


    /**
     * 单点登录的页面
     */
    public final static String SERVER_LOGIN_HTML = "/index";

    /**
     * client的退出url
     */
    public final static String CLIENT_LOGOUT_URL = "ssoClient/clientLogout";

    /**
     * 登录页面的名称
     */
    public final static String INDEX = "index";

    /**
     * 跳转到主页面
     */
    public final static String MAIN = "main";

    /**
     * 获取返回的数据的KEY
     */
    public final static String CALLBACK_DATA = "callbackData";

}
