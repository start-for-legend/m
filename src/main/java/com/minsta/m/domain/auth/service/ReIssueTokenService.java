package com.minsta.m.domain.auth.service;

import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public interface ReIssueTokenService {

    LoginResponse execute(Cookie[] cookies, HttpServletResponse response);
}
