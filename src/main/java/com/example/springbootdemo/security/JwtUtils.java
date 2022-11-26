package com.example.springbootdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private static final String ISSUER = "com.example";
    // TODO: 有効期限（min）はどこでどう持たせるべきか？application.propertiesが適切な気がする。
    private static final int EXPIRATION_MIN = 3;
    // 256byte以上
    private static final String BASE64_SECRET_KEY = "secret123456789012345678900123456789001234567890";
    private static final byte[] KEY_BYTES;
    private static final Key KEY;

    static {
        KEY_BYTES = Base64.getDecoder().decode(BASE64_SECRET_KEY);
        KEY = Keys.hmacShaKeyFor(KEY_BYTES);
    }

    public static String createToken(String loginId, BizClaims bizClaims) {
        // TODO: ZonedDateTimeについて調べる
        Date expiredDate = Date.from(
            ZonedDateTime.now().plusMinutes(EXPIRATION_MIN).toInstant());
        // toke生成
        String token = Jwts.builder()
            .setIssuer(ISSUER)
            .setSubject(loginId)
            .setExpiration(expiredDate)
            .signWith(KEY, SignatureAlgorithm.HS256)
            .addClaims(bizClaims)
            .compact();
        return token;
    }

    public static Boolean verifyToken(String token) {
        // tokenの解析
        // TODO: 不正な文字列を与えた場合、どのような振る舞いになるか確認すること
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
        Date expirationData = claims.getExpiration();
        if (expirationData.before(new Date())) {
            // TODO: 例外をthrowする作りが良いのでは。検査例外。
            System.out.println("error");
            return false;
        }
        return true;
    }

    public static Claims getClaims(String token) {
        // tokenの解析
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
        Date expirationData = claims.getExpiration();
        expirationData.before(new Date());
        String sub = claims.getSubject();
        String issuer = claims.getIssuer();
        String username = (String) claims.get("username");
        return claims;
    }

    public static BizClaims getBizClaims(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return BizClaims.getBizClaimsFromClaims(claims);
    }


}
