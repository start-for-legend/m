package com.minsta.m.domain.auth.service.impl;

import com.minsta.m.domain.auth.entity.BlackList;
import com.minsta.m.domain.auth.repository.BlackListRepository;
import com.minsta.m.domain.auth.service.LogoutService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.security.exception.TokenExpiredException;
import com.minsta.m.global.security.exception.TokenNotValidException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ServiceWithTransactional
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final BlackListRepository blackListRepository;

    @Override
    public void execute(HttpServletRequest request) {
        String token = null;

        for (Cookie cookie : request.getCookies()) {
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

        BlackList blackList = BlackList
                .builder()
                .accessToken(request.getHeader("Authorization"))
                .refreshToken(token)
                .expiredIn(1209600)
                .build();

        blackListRepository.save(blackList);
    }
}