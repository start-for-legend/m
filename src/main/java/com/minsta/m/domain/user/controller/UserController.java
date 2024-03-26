package com.minsta.m.domain.user.controller;

import com.minsta.m.domain.user.controller.data.request.LoginRequest;
import com.minsta.m.domain.user.controller.data.request.SignupRequest;
import com.minsta.m.domain.user.controller.data.request.SmsAuthRequest;
import com.minsta.m.domain.user.controller.data.request.SmsSendRequest;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import com.minsta.m.domain.user.service.LoginService;
import com.minsta.m.domain.user.service.SignupService;
import com.minsta.m.domain.user.service.SmsAuthenticationService;
import com.minsta.m.domain.user.service.SmsSendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "http://10.53.68.120:80/user 하위 API", description = "User 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SignupService signupService;
    private final LoginService loginService;
    private final SmsSendService smsSendService;
    private final SmsAuthenticationService smsAuthenticationService;



    @Operation(summary = "Signup", description = "사용자 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "Bad Request, Request 데이터 문제"),
            @ApiResponse(responseCode = "403", description = "SMS 인증이 되지 않은 사용자"),
            @ApiResponse(responseCode = "409", description = "Request Email or NickName Duplicate"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> signup(@RequestBody @Valid SignupRequest signupRequest) {
        signupService.execute(signupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Login", description = "사용자 로그인 및 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, 로그인 성공 및 토큰 발급", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "Password MisMatch"),
            @ApiResponse(responseCode = "404", description = "400과 같은 이유"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        var res = loginService.execute(loginRequest, response);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @Operation(summary = "send sms", description = "인증번호 전송")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK, 인증번호전송"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/auth")
    public ResponseEntity<HttpStatus> createAuth(@RequestBody @Valid SmsSendRequest smsSendRequest) {
        smsSendService.execute(smsSendRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "sms check", description = "인증번호 체크")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호 인증 완료 가입 진행"),
            @ApiResponse(responseCode = "403", description = "Key, MisMatch"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/check")
    public ResponseEntity<HttpStatus> checkAuth(@RequestBody @Valid SmsAuthRequest smsAuthRequest) {
        smsAuthenticationService.execute(smsAuthRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
