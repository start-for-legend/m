package com.minsta.m.domain.follow.service;

import com.minsta.m.global.entity.HeartValidResponse;

public interface FollowValidService {

    HeartValidResponse execute(Long userId);
}
