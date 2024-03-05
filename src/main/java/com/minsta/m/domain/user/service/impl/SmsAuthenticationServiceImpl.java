package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.request.SmsAuthRequest;
import com.minsta.m.domain.user.entity.SmsAuthentication;
import com.minsta.m.domain.user.exception.AuthKeyIsNotMatchedException;
import com.minsta.m.domain.user.repository.SmsAuthRepository;
import com.minsta.m.domain.user.service.SmsAuthenticationService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class SmsAuthenticationServiceImpl implements SmsAuthenticationService {

    private final SmsAuthRepository smsAuthRepository;

    @Override
    public void execute(SmsAuthRequest smsAuthRequest) {
        SmsAuthentication auth = smsAuthRepository.findByPhone(smsAuthRequest.getPhone());

        if (auth.getKey() == smsAuthRequest.getKey()) {
            auth.setCheck(true);
        } else throw new AuthKeyIsNotMatchedException();
    }
}

