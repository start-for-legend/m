package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.SignupRequest;

public interface SignupService {

    void execute(SignupRequest signupRequest);
}
