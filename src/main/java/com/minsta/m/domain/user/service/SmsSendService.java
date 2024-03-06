package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.SmsSendRequest;

public interface SmsSendService {

    void execute(SmsSendRequest smsSendRequest);
}
