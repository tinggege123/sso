package com.edu.sso.constants;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * server常量
 *
 * @author wst
 * @date 2018/9/23 14:19
 **/
@Slf4j
@Component
@ConfigurationProperties(locations = "classpath:ssoclient.properties")
public class ClientContants implements InitializingBean {

    /**
     * client.domain = http://172.22.44.147
     * client.port = 8080
     * server.domain = http://172.22.44.147
     * server.port = 8081
     *
     * @throws Exception
     */
//    @Value("client.domain")
//    private String clientDomain;
//    @Value("client.port")
    @Resource
    private ClientBean clientBean;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("====================单点登录初始化变量开始================");
        this.getPropertiesFromSsoClientFile();
        log.info("====================单点登录初始化变量结束================");
    }

    public void getPropertiesFromSsoClientFile() throws Exception {
        try {
            String clientPort = clientBean.getClientPort();
            //在域名情况下为空
            if (StringUtils.isEmpty(clientPort) || clientPort.equals("null")) {
                clientPort = "";
            } else {
                clientPort = ":" + clientPort + "/";
            }
            ClientContants.CLIENT_IP_PORT = clientBean.getClientDomain() + clientPort;
            String serverPort = clientBean.getServerPort();
            if (StringUtils.isEmpty(serverPort) || serverPort.equals("null")) {
                serverPort = "";
            } else {
                serverPort = ":" + serverPort + "/";
            }
            ClientContants.SERVER_IP_PORT = clientBean.getServerDomain() + serverPort;
            ClientContants.SERVER_AUTH_URL = ClientContants.SERVER_IP_PORT + "ssoServer/auth";

            log.info("This System ProjectName = {}",clientBean.getProjectName());
            log.info("This System publicKey = {}",clientBean.getPublicKey());
            log.info("This System IP And Port = {}", ClientContants.CLIENT_IP_PORT);
            log.info("The Login System IP And Port = {}", ClientContants.SERVER_IP_PORT);
            //tomcat用的
//            ClientContants.CLIENT_IP_PORT = "http://47.107.59.252:8080/YunSystem/";
//            ClientContants.SERVER_IP_PORT = "http://39.106.49.168:8081/";
//            ClientContants.SERVER_AUTH_URL = ClientContants.SERVER_IP_PORT + "ssoServer/auth";
//            log.info("This System ProjectName = {}","YunSystem");
//            log.info("This System publicKey = {}","dfasfdsafsafafdsadfdafdf");
//            log.info("This System IP And Port = {}", "http://47.107.59.252:8080/YunSystem/");
//            log.info("The Login System IP And Port = {}", "http://39.106.49.168:8081/");
        } catch (Exception e) {
            throw new Exception("单点登录变量初始化失败" + e);
        }
    }

    /**
     * 该项目端口
     */
    public static String CLIENT_IP_PORT = "";

//    /**
//     * 单点登录的页面
//     */
//    public final static String SERVER_LOGIN_HTML = FinalData.THIS_IP + "/server/index.html";

    /**
     * 验证token的url
     */
    public final static String IDENTITY_TOKEN_URL = "/ssoServer/getStatusByToken";

    /**
     * server的退出http
     */
    public final static String LOGIN_OUT_URL = "/ssoServer/logout";

    /**
     * 获取登录中心ip
     */
    public static String SERVER_IP_PORT = "";

    /**
     * 进行认证的ip和端口
     */
    public static String SERVER_AUTH_URL = "";

    /**
     * 定义进行参数组装跳转到ssoServer的url
     */
    public final static String AUTH_SERVER_URL = "/ssoClient/sync";

    /**
     * 临时性的url
     */
    public final static String TEMPORARY_URL = "temporary_url";

    /**
     * 同步请求url
     */
    public final static String TO_SERVER_AUTH_URL = "/ssoClient/toServerAuth";
}
