package com.rmanage.rmanage.usermanage.config.security.auth.jwt;

public class JwtProperties {
    static String SECRET = "a"; // secret key
    static int EXPIRATION_TIME = 1000 * 30 * 60; // 만료 시간
    static String TOKEN_PREFIX = "Bearer "; // 토큰
    static String HEADER_STRING = "Authorization"; // 키 값
}
