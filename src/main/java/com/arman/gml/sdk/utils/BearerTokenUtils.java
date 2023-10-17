package com.arman.gml.sdk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 签名工具包；过期时间30分钟，缓存时间29分钟
 *
 * @author Arman
 * @date 2023/10/17
 */
@Slf4j
public class BearerTokenUtils {

    private static final String ALG_H256 = "HS256";
    private static final String SIGN_TYPE = "SIGN";


    // 过期时间；默认30分钟
    private static final int expireMillis = 30 * 60 * 1000;

    // 缓存服务
    public static Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(expireMillis - (60 * 1000), TimeUnit.MINUTES)
            .build();

    /**
     * 对 ApiKey 进行签名
     *
     * @param apiKey    登录创建 ApiKey <a href="https://open.bigmodel.cn/usercenter/apikeys">apikeys</a>
     * @param apiSecret apiKey的后半部分 xxxxxxxx.yyyyyy 取 yyyyyy 使用
     * @return Token
     */
    public static String getToken(String apiKey, String apiSecret) {
        // 缓存Token
        String token = cache.getIfPresent(apiKey);
        if (null != token) return token;
        // 创建Token
        Algorithm algorithm = Algorithm.HMAC256(apiSecret.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> payload = new HashMap<>();
        payload.put("api_key", apiKey);
        // 过期时间
        long timeMillis = System.currentTimeMillis();
        payload.put("exp", timeMillis + expireMillis);
        payload.put("timestamp", timeMillis);
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", ALG_H256);
        headerClaims.put("sign_type", SIGN_TYPE);
        token = JWT.create()
                .withPayload(payload)
                .withHeader(headerClaims)
                .sign(algorithm);

        cache.put(apiKey, token);
        return token;
    }

}

