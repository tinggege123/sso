package com.edu.sso.constants;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 从配置文件读取值
 *
 * @author wst
 * @date 2018/12/9 15:24
 **/
@Data
@Component
@ConfigurationProperties(locations = "classpath:ssoclient.properties", prefix = "sso")
public class ClientBean {

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 子系统的IP
     */
    private String clientDomain;

    /**
     * 子系统的端口
     */
    private String clientPort;

    /**
     * 登录中心的IP
     */
    private String serverDomain;

    /**
     * 登录中的端口
     */
    private String serverPort;

    /**
     * 公钥
     */
    private String publicKey;




//    /**
//     * 构造支付交易请求参数
//     * @param orderNo 交易订单号
//     * @param body 交易参数
//     * @param terminalNo 机构终端号
//     */
//    public static WsHeadBodyVO<PaymentWrapHead, String> buildPaymentTradeRequestParams(String orderNo, Object body,String terminalNo) throws Exception {
//        WsHeadBodyVO<PaymentWrapHead, String> req = new WsHeadBodyVO<>();
//
//        String privateKey = appSettingService.getValue(AppSettingConstants.WITHHOLD_PRI_KEY);
//        String merchantNo = appSettingService.getValue(AppSettingConstants.WITHHOLD_MERCHANTNO);
//        String platform = appSettingService.getValue(AppSettingConstants.WITHHOLD_PLATFORM);
//        String salt = appSettingService.getValue(AppSettingConstants.WITHHOLD_SALT);
//        String aesKey = appSettingService.getValue(AppSettingConstants.WITHHOLD_AES);
//        // 1.加签
//        String jsonStr = JSON.toJSONString(body) + salt;
//        String encryptContent = EncryptUtils.encrypt(jsonStr, aesKey);
//        String sign = SignUtils.sign(encryptContent, privateKey);
//        // 2.填充头信息
//        PaymentWrapHead reqHead = PaymentWrapHead.buildDefault();
//        reqHead.setSign(sign);
//        reqHead.setMerchantNo(merchantNo);
//        reqHead.setPlatformNo(platform);
//        reqHead.setReqNo(orderNo);
//        if(StringUtils.isNotBlank(terminalNo)){
//            reqHead.setTerminalNo(terminalNo);
//        }
//        reqHead.setRequestTime(Calendar.getInstance().getTimeInMillis());
//        // 3. 填充请求体信息
//        req.setHead(reqHead);
//        req.setBody(encryptContent);
//        return req;
//    }
//
//    /**
//     * 解密支付交易请求返回data结果
//     * @param view 加密内容
//     */
//    public static String decryptPaymentTradeResponseData(String view) {
//        try {
//            String aesKey = appSettingService.getValue(AppSettingConstants.WITHHOLD_AES);
//            String content = EncryptUtils.decrypt(view, aesKey);
//            log.info("[解析支付交易响应data内容]====解析响应内容 view={}, content={}", view, content);
//            if (StringUtils.isNotBlank(content)) {
//                String salt = appSettingService.getValue(AppSettingConstants.WITHHOLD_SALT);
//                return content.substring(0, content.indexOf(salt));
//            }
//        } catch (Exception e) {
//            log.error("[解析支付交易响应data内容]====解析响应内容异常 view={}", view, e);
//        }
//        return null;
//    }
}
