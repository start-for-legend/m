package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.domain.follow.entity.Follow;
import com.minsta.m.domain.follow.entity.FollowEmbedded;
import com.minsta.m.domain.follow.repository.FollowRepository;
import com.minsta.m.domain.follow.service.FollowCancelService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FollowCancelServiceImpl implements FollowCancelService {
    
    private final UserUtil userUtil;
    private final FollowRepository followRepository;

    @Override
    public void execute(Long userId) {
        Follow follow = followRepository.findByFollowEmbedded(new FollowEmbedded(
                userUtil.getUser().getUserId(),
                userId
        ));
        followRepository.delete(follow);
    }
}
