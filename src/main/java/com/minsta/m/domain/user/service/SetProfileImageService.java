package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.AwsUrlRequest;

public interface SetProfileImageService {

    void execute(AwsUrlRequest awsUrlRequest);
}
