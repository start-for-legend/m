package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.SmsAuthRequest;

public interface SmsAuthenticationService {

    void execute(SmsAuthRequest smsAuthRequest);
}
