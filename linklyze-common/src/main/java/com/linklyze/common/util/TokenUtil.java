package com.linklyze.common.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author novo
 * @since 2023-03-06
 */
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static final String SUBJECT = "plato-link";

    // TODO token过期时间，签名 硬编码
    private static final String ISSUER = "WYU";

    // 2h
    private static final Integer accessExpiredTime = 60 * 60 * 1112;

    private static Integer refreshExpiredTime;

//    @Value("${token.access.expired}")
//    public void setAccessExpiredTime(Integer accessExpiredTime) {
//        TokenUtil.accessExpiredTime = accessExpiredTime;
//    }
//
//    @Value("${token.refresh.expired}")
//    public void setRefreshExpiredTime(Integer refreshExpiredTime) {
//        TokenUtil.refreshExpiredTime = refreshExpiredTime;
//    }

    private static String generateToken(String tokenType, Map<String, Object> claims, Integer expiredTime) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        // 当前时间加上过期时间就是过期的时间点
        calendar.add(Calendar.SECOND, expiredTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token_type", tokenType);
        map.put("account", claims);
        return Jwts.builder()
                .setClaims(map) // setClaims要第一步执行否则会覆盖掉其他属性
                .setIssuer(ISSUER)
                .setSubject(SUBJECT)
                .setIssuedAt(now)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey())
                .compact();
    }

    private static String generateToken(String tokenType, Long userId, Integer expiredTime) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        // 当前时间加上过期时间就是过期的时间点
        calendar.add(Calendar.SECOND, expiredTime);
        Map<String, Object> claims = new HashMap<>();
        claims.put("account_no", userId);
        claims.put("token_type", tokenType);
        return Jwts.builder()
                .setClaims(claims) // setClaims要第一步执行否则会覆盖掉其他属性
                .setIssuer(ISSUER)
                .setSubject(SUBJECT)
                .setIssuedAt(now)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey())
                .compact();
    }

    public static String generateAccessToken(Long userId) {
        return TokenUtil.generateToken("access", userId, TokenUtil.accessExpiredTime);
    }

    public static String generateAccessToken(Map<String, Object> claims) {
        return TokenUtil.generateToken("access", claims, TokenUtil.accessExpiredTime);
    }

    public static String generateRefreshToken(Long userId) {
        return TokenUtil.generateToken("refresh", userId, TokenUtil.refreshExpiredTime);
    }

    public static String generateRefreshToken(Map<String, Object> claims) {
        return TokenUtil.generateToken("refresh", claims, TokenUtil.refreshExpiredTime);
    }

//    public static Tokens generateDoubleToken(Long userId) throws Exception {
//        String accessToken = TokenUtil.generateAccessToken(userId);
//        String refreshToken = TokenUtil.generateRefreshToken(userId);
//        return new Tokens(accessToken, refreshToken);
//    }

    public static Claims verifyToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(RSAUtil.getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.error("jwt过期:", e);
            return null;
        } catch (Exception e) {
            logger.error("jwt解析异常:", e);
            return null;
        }
    }
}
