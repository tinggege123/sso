package com.edu.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * base加解密
 *
 * @author wst
 * @date 2018/11/25 16:14
 **/
public class BaseUtil {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key){
        try{
            String decode = URLDecoder.decode(key);
            return new String((new BASE64Decoder()).decodeBuffer(decode));
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtils.defaultString("");
    }

    /**
     * BASE64加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String str){
        try{
            String s = (new BASE64Encoder()).encodeBuffer(str.getBytes());
            return URLEncoder.encode(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtils.defaultString("");
    }
}
