package com.edu.constants;

/**
 * 加密算法的静态变量
 *
 * @author wst
 * @date 2018/12/9 19:43
 **/
public class SecurityContants {
    public static final String VERSION = "V1.0";
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final String KEY_ALGORITHM_AES = "AES";
    public static final int RSA_KEYSIZE_1024 = 1024;
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String AES_ALGORITHM_MODE = "AES/ECB/PKCS5Padding";
    public static final String KEY_SIGN = "sign";

    public SecurityContants() {
    }
}
