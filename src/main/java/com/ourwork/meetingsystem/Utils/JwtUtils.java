package com.ourwork.meetingsystem.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 假设这是你的签名密钥
    private static final String SECRET_KEY = "lyyweblyyweblyyweblyyweblyyweblyyweblyyweblyyweblyyweb";
    private static final Key signKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    // 假设这是过期时间，单位为毫秒
    private static final long expire = 1000 * 60 * 60;
    public int generateJwt;

    /**
     * 生成JWT令牌
     * @param claims 自定义声明
     * @return JWT令牌
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(signKey, SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}