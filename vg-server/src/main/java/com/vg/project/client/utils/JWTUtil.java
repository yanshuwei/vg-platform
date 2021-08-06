package com.vg.project.client.utils;


import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: James
 * @Date: 2019/3/7 11:47
 * @Description:
 */
public class JWTUtil {
    private static final String SECRET = "Goldpeak&$^&@KL<><MQLMNQNQJQK+_()#$";

    private static final String EXPIRED = "expired";

    private static final String PAYLOAD = "payload";

    /**
     * 生成token
     *
     * @param object
     * @param maxAge
     * @param <T>
     * @return
     */
    public static <T> String sign(T object, long maxAge) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXPIRED, String.valueOf(System.currentTimeMillis() + maxAge));
            return signer.sign(claims);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token
     *
     * @param <T>
     * @param token
     * @param classT
     * @return
     */
    public static <T> T unsign(String token, Class<T> classT) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> claims = verifier.verify(token);
            if (claims.containsKey(EXPIRED) && claims.containsKey(PAYLOAD)) {
                long expired = Long.parseLong(claims.get(EXPIRED).toString());
                long currentTimeMillis = System.currentTimeMillis();
                if (expired > currentTimeMillis) {
                    String jsonString = (String) claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(jsonString, classT);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}