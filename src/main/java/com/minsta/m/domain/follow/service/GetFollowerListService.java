package com.minsta.m.domain.follow.service;

import com.minsta.m.domain.user.controller.data.response.UserResponse;

import java.util.List;

public interface GetFollowerListService {

    List<UserResponse> execute(Long userId, Long lastUserId);
}
