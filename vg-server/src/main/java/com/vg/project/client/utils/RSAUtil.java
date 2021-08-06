package com.vg.project.client.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * RSA加密公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkoEBPZ2OFM0SSJEu7pNSaFj9kDUmApwr8r7wn0nEVNwlYnmepGsJ1i/vObsNOJ44TdS9GbfILRf89LQeW4qOHQ3gyoXVPStMBOeQDiE+ERzMPFiUYQyoV2XBTIoCRCFV/giS6uINrxfqGx3riqRlAhasscuaH2DgOuASmFqei/wIDAQAB";
    /**
     * RSA加密私钥
     */
    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKSgQE9nY4UzRJIkS7uk1JoWP2QNSYCnCvyvvCfScRU3CVieZ6kawnWL+85uw04njhN1L0Zt8gtF/z0tB5bio4dDeDKhdU9K0wE55AOIT4RHMw8WJRhDKhXZcFMigJEIVX+CJLq4g2vF+obHeuKpGUCFqyxy5ofYOA64BKYWp6L/AgMBAAECgYEAiPwDp07rg9m+NDDZh8FHWxQqGVHpGJFm1g5Q7X3xtOp/72qX7SGkL/WUSRjnkO/fDBfVh7BS2Mic80W16/qYgMcsy7OApXFgNbjOoG0wlr9mBFib7uBjArpnk2RGMHmMNc2BiClm6vlEgptY8TuJgjjngPn4ikXBy5HwSXFZL+ECQQD84dGIQro3LPeOYWYfPus0u0KBav7ZxAHHeeCRB25q7ctFa8OmiIjXz+WocMYhr7OcE1AUfb2D4Roj9OxI2ftPAkEApqfd1QJ9CCruZTggrlEOnr9ONspkKfa6AFkMvhwL3MC8iv2i7ZMU/ZhCJ3ME7/QG8kA/67ovfOAjcaDubNsxUQJBAPcDXbjL9TmQIwBhA2/h9b48bGoeKXJtk49V7ZW8vI6WWMgmy6YQGBs2aA5wmhitl7QM6VwattPrPa2BwIwo4OECQQCPTIhSQHGCNGgIYNq5CLmphpWaRy0ZaC1HX0q1rSgGCTQVz0HpRi8mX2WCxkPYj9vpMZXE+7MZjSDs85QPxeoBAkBONxcocK0nqSRu9yn5geHyJ0q0Q+mEL7QScAHn3vMatX+Y/0S8a1gxR6tPShSHmC7bG6lpygoUp9O3ND3/Y+ok";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    //获得公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }


    //获得私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获取公钥
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //获取私钥
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }


    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //签名
    public static byte[] sign(byte[] data, String privateKeyStr) throws Exception {
        PrivateKey priK = getPrivateKey(privateKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return sig.sign();
    }

    // 验证
    public static boolean verify(byte[] data, byte[] sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(publicKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    // 加密
    private static byte[] encrypt(Key key, byte[] obj, int offSet, int endSet) {
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return cipher.doFinal(obj, offSet, endSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 解密
    private static byte[] decrypt(Key key, byte[] obj, int offSet, int endSet) {
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key);
                return cipher.doFinal(obj, offSet, endSet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 服务端私钥加密
    public static String serverEncrypt(String value) {
        if (RSAUtil.isEmpty(value)) {
            return null;
        }
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey(PRIVATE_KEY);
            value = java.net.URLEncoder.encode(value, "utf-8");
            byte[] data = value.getBytes();
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = encrypt(privateKey, data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = encrypt(privateKey, data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] enByte = out.toByteArray();
            out.close();
            return encryptBASE64(enByte).replaceAll("\\r|\\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 服务端私钥解密
    public static String serverDecrypt(String value) {
        if (RSAUtil.isEmpty(value)) {
            return null;
        }
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey(PRIVATE_KEY);
            byte[] enBytes = decryptBASE64(value);
            int inputLen = enBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = decrypt(privateKey, enBytes, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = decrypt(privateKey, enBytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] deByte = out.toByteArray();
            out.close();
            return java.net.URLDecoder.decode(new String(deByte), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 客户端公钥加密
    public static String clientEncrypt(String value) {
        if (RSAUtil.isEmpty(value)) {
            return null;
        }
        try {
            RSAPublicKey publicKey = (RSAPublicKey) getPublicKey(PUBLIC_KEY);
            value = java.net.URLEncoder.encode(value, "utf-8");
            byte[] data = value.getBytes();
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = encrypt(publicKey, data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = encrypt(publicKey, data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] enByte = out.toByteArray();
            out.close();
            return encryptBASE64(enByte).replaceAll("\\r|\\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 客户端公钥解密
    public static String clientDecrypt(String value) {
        if (RSAUtil.isEmpty(value)) {
            return null;
        }
        try {
            RSAPublicKey publicKey = (RSAPublicKey) getPublicKey(PUBLIC_KEY);
            byte[] enBytes = decryptBASE64(value);
            int inputLen = enBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = decrypt(publicKey, enBytes, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = decrypt(publicKey, enBytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] deByte = out.toByteArray();
            out.close();
            return java.net.URLDecoder.decode(new String(deByte), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0) || ("".equals(str)) || ("null".equals(str)));
    }

    public static void main(String[] args) {
        String input = "{\"readFlag\": 0}";
        try {
            //加密后的东西
            System.out.println("客户端加密密文=======" + clientEncrypt(input));
            //开始解密
            System.out.println("服务端解密明文=====" + serverDecrypt("ATTEaPy5jWTNgLGpFhzZdJR7lTYFP7ofxj676CKWe4EAI8pHLmwu9hc3ESHnIc8YOLLcv60i61o+BofBRk+dae/uM4uEAV0dg3WCPbDi64bgZ4mpzLdt2TS89JdID1lyEx+33b7Kl+ZdBEHklPIufKGThh0UBsd1JSQciNNGuWU="));

            System.out.println("服务端加密=====" + serverEncrypt(input));

            System.out.println("客户端解密明文=======" + clientDecrypt("aOPsmv5CHJDhm4oLBktpSs0dyPy4J5WmiLjaulIovfLdAtoBaEaUZThT9TLLtgwakaLzu9MR0bgnWDWaexHVFZH26RzCCtcGQDc6vRSUGFMsDoL+wcj2mqJPc3b5GoQD1RLT5qxLYzFupExKhmDzLPjEw0Avo0cOgEUnIZAqvelWeZ7quKJnUD1DOkPX7oOpl2MxnyTG5T0LAAoEkxoJW+IjplDwKvJcrSiFOkz6iV86I/Mif6cjz+B08k556J2tn4suhs8leKkbDOF2EcVGdlDbSfZ3AZ4m2GoPjvOZLsOvFuyxiHE+V/2fiYSMlxkxI6Mr7AbOjQEfMOb265RlcA=="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}