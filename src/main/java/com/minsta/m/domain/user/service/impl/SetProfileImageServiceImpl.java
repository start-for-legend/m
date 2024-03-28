package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.request.AwsUrlRequest;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.domain.user.service.SetProfileImageService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class SetProfileImageServiceImpl implements SetProfileImageService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Override
    public void execute(AwsUrlRequest awsUrlRequest) {
        User currentUser = userUtil.getUser();
        currentUser.setProfileUrl(awsUrlRequest.awsUrl());
        userRepository.save(currentUser);
    }
}
