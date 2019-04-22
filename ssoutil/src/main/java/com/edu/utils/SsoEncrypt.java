package com.edu.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SsoEncrypt {
    static final String ALGORITHM_RSA = "RSA";
    static final String ALGORITHM_SIGN = "MD5withRSA";
//    public static Map<String, String> view = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 生成公钥和私钥
        List<String> list = generatorKeyPair();

        String publicKey = list.get(0);

        String privateKey = list.get(1);

        String source = "我是程序猿！";
        System.out.println("加密前的数据：\r\n" + source);
        System.out.println("--------------------------公钥加密，私钥解密------------------------------");
        // 公钥加密
        String target = encryptionByPublicKey(source, publicKey);
        // 私钥解密
        String data = decryptionByPrivateKey(target, privateKey);

        System.out.println("--------------------------私钥加密并且签名，公钥验证签名并且解密------------------------------");
        // 私钥加密
        target = encryptionByPrivateKey(source, privateKey);
        // 签名
        String sign = signByPrivateKey(target, privateKey);
        // 验证签名
        boolean b = verifyByPublicKey(target, sign, publicKey);
        // 公钥解密
        String s = decryptionByPublicKey(target, publicKey);

        System.out.print("");
    }

    /**
     * 公钥加密
     *
     * @param source    加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String clientEncryptionByPublicKey(String source, String publicKey) throws Exception {
        return encryptionByPublicKey(source, publicKey);
    }

    /**
     * 私钥解密
     *
     * @param target
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String serverDecryptionByPrivateKey(String target, String privateKey) throws Exception {
        return decryptionByPrivateKey(target, privateKey);
    }

    /**
     * 私钥加密
     *
     * @param target
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String serverEncryptionByPrivateKey(String target, String privateKey) throws Exception {
        return encryptionByPrivateKey(target, privateKey);
    }

    /**
     * 公钥解密
     *
     * @param target
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String clientDecryptionByPublicKey(String target, String publicKey) throws Exception {
        // 公钥解密
        return decryptionByPublicKey(target, publicKey);
    }

    /**
     * 生成密钥对(公钥，私钥)
     *
     * @throws Exception
     */
    public static List<String> generatorKeyPair() throws Exception {
        List<String> list = new ArrayList<>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] keyBs = rsaPublicKey.getEncoded();
        list.add(encodeBase64(keyBs));
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        keyBs = rsaPrivateKey.getEncoded();
        list.add(encodeBase64(keyBs));
        return list;
    }

    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 获取私钥
     *
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 公钥加密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encryptionByPublicKey(String source, String basePublicKey) throws Exception {
        PublicKey publicKey = getPublicKey(basePublicKey);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
        System.out.println("公钥加密后的数据：\r\n" + target);
        return target;
    }

    /**
     * 公钥解密
     *
     * @param target
     * @throws Exception
     */
    public static String decryptionByPublicKey(String target, String basePublicKey) throws Exception {
        PublicKey publicKey = getPublicKey(basePublicKey);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
        System.out.println("公钥解密后的数据：\r\n" + source);
        return source;
    }

    /**
     * 公钥验证签名
     *
     * @return
     * @throws Exception
     */
    public static boolean verifyByPublicKey(String target, String sign, String basePublicKey) throws Exception {
        PublicKey publicKey = getPublicKey(basePublicKey);
        Signature signature = Signature.getInstance(ALGORITHM_SIGN);
        signature.initVerify(publicKey);
        signature.update(target.getBytes("UTF-8"));
        if (signature.verify(decodeBase64(sign))) {
            System.out.println("签名正确！");
            return true;
        } else {
            System.out.println("签名错误！");
            return false;
        }
    }

    /**
     * 私钥加密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encryptionByPrivateKey(String source, String basePrivateKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(basePrivateKey);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
        System.out.println("私钥加密后的数据：\r\n" + target);
        return target;
    }

    /**
     * 私钥解密
     *
     * @param target
     * @throws Exception
     */
    public static String decryptionByPrivateKey(String target, String basePrivateKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(basePrivateKey);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
        System.out.println("私钥解密后的数据：\r\n" + source);
        return source;
    }

    /**
     * 私钥签名
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static String signByPrivateKey(String target, String basePrivateKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(basePrivateKey);
        Signature signature = Signature.getInstance(ALGORITHM_SIGN);
        signature.initSign(privateKey);
        signature.update(target.getBytes("UTF-8"));
        String sign = encodeBase64(signature.sign());
        System.out.println("生成的签名：\r\n" + sign);
        return sign;
    }

    /**
     * base64编码
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[] source) throws Exception {
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

    /**
     * Base64解码
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String target) throws Exception {
        return Base64.decodeBase64(target.getBytes("UTF-8"));
    }
}
