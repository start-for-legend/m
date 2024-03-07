package com.minsta.m.domain.auth.service.impl;

import com.minsta.m.common.cookie.CookieManager;
import com.minsta.m.domain.auth.repository.BlackListRepository;
import com.minsta.m.domain.auth.service.ReIssueTokenService;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.security.exception.TokenExpiredException;
import com.minsta.m.global.security.exception.TokenNotValidException;
import com.minsta.m.global.security.jwt.TokenIssuer;
import com.minsta.m.global.security.jwt.TokenParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@ServiceWithTransactional
@RequiredArgsConstructor
public class ReIssueTokenServiceImpl implements ReIssueTokenService {

    private final TokenIssuer tokenIssuer;
    private final TokenParser tokenParser;
    private final BlackListRepository blackListRepository;
    private final CookieManager cookieManager;

    @Override
    public LoginResponse execute(Cookie[] cookies, HttpServletResponse response) {
        String token = null;

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            throw new TokenNotValidException();
        }

        if (blackListRepository.existsByRefreshToken(token)) {
            throw new TokenExpiredException();
        }

        String phone = tokenParser.extractEmailWithClaim(token);
        cookieManager.addCookie(tokenIssuer.generateRefreshToken(phone), response);

        return LoginResponse.builder()
                .accessToken(tokenIssuer.generateAccessToken(phone))
                .expiredAt(ZonedDateTime.now().plusSeconds(tokenIssuer.getExpiredTime()))
                .build();
    }
}
