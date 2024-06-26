package com.minsta.m.common.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieManager {

    @Value("${server.cookie.domain}")
    private String domain;

    private Cookie setCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);

        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1209600);

        return cookie;
    }

    public void addCookie(String refreshToken, HttpServletResponse response) {
        response.addCookie(setCookie(refreshToken));
    }
}
