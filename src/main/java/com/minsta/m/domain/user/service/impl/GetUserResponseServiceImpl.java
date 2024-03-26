package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.service.GetUserResponseService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class GetUserResponseServiceImpl implements GetUserResponseService {

    private final UserUtil userUtil;

    @Override
    public UserResponse execute() {

        User user = userUtil.getUser();

        return UserResponse.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
