package com.minsta.m.domain.user.service.impl;

import com.minsta.m.common.cookie.CookieManager;
import com.minsta.m.domain.user.controller.data.request.LoginRequest;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.exception.InvalidPasswordException;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.domain.user.service.LoginService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.security.exception.UserNotFoundException;
import com.minsta.m.global.security.jwt.TokenIssuer;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieManager cookieManager;
    private final TokenIssuer tokenIssuer;

    @Override
    public LoginResponse execute(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByPhone(loginRequest.getPhone())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        cookieManager.addCookie(tokenIssuer.generateRefreshToken(loginRequest.getPhone()), response);

        return LoginResponse.builder()
                .accessToken(tokenIssuer.generateAccessToken(loginRequest.getPhone()))
                .expiredAt(ZonedDateTime.now().plusSeconds(tokenIssuer.getExpiredTime()))
                .build();
    }
}
