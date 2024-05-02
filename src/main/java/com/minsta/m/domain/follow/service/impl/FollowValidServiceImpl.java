package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.domain.follow.entity.FollowEmbedded;
import com.minsta.m.domain.follow.repository.FollowRepository;
import com.minsta.m.domain.follow.service.FollowValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class FollowValidServiceImpl implements FollowValidService {

    private final UserUtil userUtil;
    private final FollowRepository followRepository;

    @Override
    public HeartValidResponse execute(Long userId) {

        if (followRepository.existsById(new FollowEmbedded(userUtil.getUser().getUserId(), userId))) {
            return HeartValidResponse.of(true);
        } else return HeartValidResponse.of(false);
    }
}
