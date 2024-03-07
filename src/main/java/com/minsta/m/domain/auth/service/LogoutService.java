package com.minsta.m.domain.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface LogoutService {

    void execute(HttpServletRequest request);
}
