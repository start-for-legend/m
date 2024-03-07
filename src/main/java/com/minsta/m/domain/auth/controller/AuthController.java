package com.minsta.m.domain.auth.controller;

import com.minsta.m.domain.auth.service.LogoutService;
import com.minsta.m.domain.auth.service.ReIssueTokenService;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final ReIssueTokenService reIssueTokenService;
    private final LogoutService logoutService;

    @PatchMapping
    public ResponseEntity<LoginResponse> reIssueToken(HttpServletRequest request, HttpServletResponse response) {
        var res = reIssueTokenService.execute(request.getCookies(), response);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
        logoutService.execute(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
