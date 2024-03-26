package com.minsta.m.global.security.jwt;

import com.minsta.m.global.security.jwt.properties.JwtProperties;
import com.minsta.m.global.security.jwt.properties.TokenTimeProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
@RequiredArgsConstructor
public class TokenIssuer {

    private final JwtProperties jwtProperties;
    private final TokenTimeProperties tokenTimeProperties;

    public long getExpiredTime() {
        return 1000 * 60 * 120;
    }

    @AllArgsConstructor
    private enum TokenObject {
        ACCESS_TYPE("access"),
        REFRESH_TYPE("refresh"),
        TOKEN_PREFIX("Bearer "),
        AUTHORITY("authority");
        final String value;
    }

    public String generateToken(String phone, String type, Key secret, Long exp) {

        final Claims claims = Jwts.claims().setSubject(phone);

        claims.put("type", type);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(secret, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String generateAccessToken(String phone) {
        return generateToken(phone, TokenObject.ACCESS_TYPE.value, jwtProperties.getAccessSecret(), tokenTimeProperties.getAccessTime());
    }

    public String generateRefreshToken(String phone) {
        return generateToken(phone, TokenObject.REFRESH_TYPE.value, jwtProperties.getRefreshSecret(), tokenTimeProperties.getRefreshTime());
    }
}
