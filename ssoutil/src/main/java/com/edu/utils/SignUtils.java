package com.edu.utils;


import com.edu.constants.SecurityContants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名工具类
 *
 * @author wst
 * @date 2018年6月16日
 */
public class SignUtils {

    private final static Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String sign(String content, String privateKey) {

        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));

            KeyFactory keyf = KeyFactory.getInstance(SecurityContants.KEY_ALGORITHM_RSA);

            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature sign = Signature.getInstance(SecurityContants.SIGNATURE_ALGORITHM);

            sign.initSign(priKey);

            sign.update(content.getBytes(SecurityContants.CHARSET_UTF8));

            byte[] signed = sign.sign();

            return new String(Base64.encodeBase64(signed));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 验证签名
     *
     * @param content
     * @param signString
     * @param publicKey
     * @return
     */
    public static boolean checkSign(String content, String signString, String publicKey) {

        try {

            KeyFactory keyFactory = KeyFactory.getInstance(SecurityContants.KEY_ALGORITHM_RSA);

            byte[] encodedKey = Base64.decodeBase64(publicKey);

            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature sign = Signature.getInstance(SecurityContants.SIGNATURE_ALGORITHM);
            sign.initVerify(pubKey);
            sign.update(content.getBytes(SecurityContants.CHARSET_UTF8));

            boolean bverify = sign.verify(Base64.decodeBase64(signString));

            return bverify;

        } catch (Exception e) {
            logger.error("验签异常", e);
        }

        return false;

    }

}
