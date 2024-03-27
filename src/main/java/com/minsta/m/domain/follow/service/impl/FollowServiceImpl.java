package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.domain.follow.entity.Follow;
import com.minsta.m.domain.follow.entity.FollowEmbedded;
import com.minsta.m.domain.follow.repository.FollowRepository;
import com.minsta.m.domain.follow.service.FollowService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.redis.RedisList;
import com.minsta.m.global.security.exception.UserNotFoundException;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FollowServiceImpl implements FollowService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final RedisList redisList;

    @Override
    public void execute(Long userId) {
        User followed = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Follow follow = Follow.builder()
                .followEmbedded(new FollowEmbedded(
                        userUtil.getUser().getUserId(),
                        userId
                ))
                .user(userUtil.getUser())
                .followedUser(followed)
                .build();

        followRepository.save(follow);
        storeRedis(followed);
    }

    private void storeRedis(User user) {
        redisList.addFollowing(String.valueOf(user.getUserId()), user.getNickName());
    }
}
