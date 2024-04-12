package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.response.UserDetailResponse;

public interface GetUserDetailService {

    UserDetailResponse execute(Long userId);
}
