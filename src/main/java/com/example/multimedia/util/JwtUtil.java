package com.example.multimedia.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CookiesEason
 * 2018/05/20 13:37
 */
@Slf4j
public class JwtUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    public static String generateToken(Authentication authentication){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",authentication.getName());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, TOKEN_PREFIX)
                .setExpiration(generateExpirationDate())
                .compact();

    }

    public static boolean parseToken(String token){
        try {
            Jwts.parser().setSigningKey(TOKEN_PREFIX).parse(token);
            return true;
        }catch (Exception e){
            log.error("Token 验证错误");
        }
        return false;
    }
    public static String getUsername(String token){
        try {
            return (String) Jwts.parser().setSigningKey(TOKEN_PREFIX).parseClaimsJws(token).getBody().get("username");
        }catch (Exception e){
            return null;
        }
    }
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 9600*1000);
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser().setSigningKey(TOKEN_PREFIX).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
