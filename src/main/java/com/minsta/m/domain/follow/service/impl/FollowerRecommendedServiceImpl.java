package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.domain.follow.controller.data.response.RecommendedFollowerResponse;
import com.minsta.m.domain.follow.service.FollowRecommendedService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.redis.RedisList;
import com.minsta.m.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ReadOnlyService
@RequiredArgsConstructor
public class FollowerRecommendedServiceImpl implements FollowRecommendedService {

    private final UserRepository userRepository;
    private final RedisList redisList;

    @Override
    public List<RecommendedFollowerResponse> execute() {
        long[] userIds = redisList.get();

        return Arrays.stream(userIds)
            .mapToObj(id -> userRepository.findById(id).orElseThrow(UserNotFoundException::new))
            .map(this::convert)
            .collect(Collectors.toList());
    }

    private RecommendedFollowerResponse convert(User user) {
        return RecommendedFollowerResponse.builder()
                .userId(user.getUserId())
                .profileUrl(user.getProfileUrl())
                .nickName(user.getNickName())
                .build();
    }
}
