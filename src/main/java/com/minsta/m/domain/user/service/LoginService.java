package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.LoginRequest;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

    LoginResponse execute(LoginRequest loginRequest, HttpServletResponse response);
}
