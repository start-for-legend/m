package com.minsta.m.domain.user.controller;

import com.minsta.m.domain.user.controller.data.request.SignupRequest;
import com.minsta.m.domain.user.controller.data.request.SmsAuthRequest;
import com.minsta.m.domain.user.controller.data.request.SmsSendRequest;
import com.minsta.m.domain.user.service.SignupService;
import com.minsta.m.domain.user.service.SmsAuthenticationService;
import com.minsta.m.domain.user.service.SmsSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SignupService signupService;
    private final SmsSendService smsSendService;
    private final SmsAuthenticationService smsAuthenticationService;

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> signup(@RequestBody @Valid SignupRequest signupRequest) {
        signupService.execute(signupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<HttpStatus> createAuth(@RequestBody @Valid SmsSendRequest smsSendRequest) {
        smsSendService.execute(smsSendRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/check")
    public ResponseEntity<HttpStatus> checkAuth(@RequestBody @Valid SmsAuthRequest smsAuthRequest) {
        smsAuthenticationService.execute(smsAuthRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
