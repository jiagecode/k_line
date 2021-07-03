package com.line.backstage.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * JWT工具类
 * @author pc
 */
@Slf4j
public class JwtUtil {

    /**
     *  过期毫秒数
     */
    private static final long EXPIRE_TIME = 1 * 24 * 60 * 60 * 1000;

    /**
     * JWT中保存的用户名键
     */
    private static final String USER_ID = "id";

    /**
     * 生成签名
     * @param userId 用户id
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String userId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 设置token和过期时间
        return JWT.create()
                .withClaim(USER_ID, userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     * @param token  密钥
     * @param userId 用户ID
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verifyToKen(String token, String userId, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(USER_ID, userId)
                    .build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("token验证失败", e);
            return false;
        }
    }

    /**
     * token验证token
     * @param token
     * @return
     */
    public static boolean verifyToKen(String token) {
        try {
            JWT.decode(token);
            return true;
        } catch (Exception e) {
            log.error("token验证失败", e);
            return false;
        }
    }

    /**
     * 获取token中的用户ID
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
