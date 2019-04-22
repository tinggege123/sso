package com.edu.ssoserver.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * 加密的东西
 *
 * @author wst
 * @date 2019/3/30 09:12
 **/
@Slf4j
public class MiUtils {


    //静态方法，便于作为工具类
    public static String getMd5(String plainText) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return plainText;
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < 100; i++) {
            String md5 = MiUtils.getMd5(1 + "");
            log.info("[{}]", md5);
        }
    }

}
