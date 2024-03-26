package com.minsta.m.global.security.jwt;

import com.minsta.m.global.security.auth.AuthDetailsService;
import com.minsta.m.global.security.exception.TokenExpiredException;
import com.minsta.m.global.security.exception.TokenNotValidException;
import com.minsta.m.global.security.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@RequiredArgsConstructor
public class TokenParser {

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;

    @AllArgsConstructor
    private enum TokenObject {
        ACCESS_TYPE("access"),
        REFRESH_TYPE("refresh"),
        TOKEN_PREFIX("Bearer "),
        AUTHORITY("authority");
        final String value;
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return null;
        }

        return parseToken(token);
    }

    public String parseToken(String token) {
        if(token.startsWith(TokenObject.TOKEN_PREFIX.value)) {
            return token.replace(TokenObject.TOKEN_PREFIX.value, "");

        } else return null;
    }

    private Claims getTokenBody(String token, Key secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();

        } catch (JwtException e) {
            throw new TokenNotValidException();
        }
    }

    public String extractEmailWithClaim(String token) {
        return getTokenBody(token, jwtProperties.getRefreshSecret()).getSubject();
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenBody(token, jwtProperties.getAccessSecret()).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
