package com.minsta.m.domain.auth.controller;

import com.minsta.m.domain.auth.service.LogoutService;
import com.minsta.m.domain.auth.service.ReIssueTokenService;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "http://10.53.68.120:80/auth 하위 API", description = "Auth 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final ReIssueTokenService reIssueTokenService;
    private final LogoutService logoutService;


    @Operation(summary = "reissue token", description = "유저 토큰 재발급")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class)),
                    headers = @Header(name = "refreshToken", description = "refreshToken value", required = true)
            ),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })

    @PatchMapping
    public ResponseEntity<LoginResponse> reIssueToken(HttpServletRequest request, HttpServletResponse response) {
        var res = reIssueTokenService.execute(request.getCookies(), response);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @Operation(summary = "logout", description = "유저 로그아웃")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "NO_CONTENT",
                    headers = @Header(name = "refreshToken", description = "refreshToken value", required = true)
            ),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @DeleteMapping
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
        logoutService.execute(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
