package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.service.GetUserResponseService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.minsta.m.domain.follow.entity.QFollow.follow;

@ReadOnlyService
@RequiredArgsConstructor
public class GetUserResponseServiceImpl implements GetUserResponseService {

    private final UserUtil userUtil;
    private final JPAQueryFactory em;

    @Override
    public UserResponse execute() {

        User user = userUtil.getUser();

        return UserResponse.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .profileUrl(user.getProfileUrl())
                .follower(getFollower(user.getUserId()))
                .following(getFollowing(user.getUserId()))
                .build();
    }

    private int getFollower(Long userId) {
        return em.selectFrom(follow)
                .where(follow.followEmbedded.followerId.eq(userId))
                .fetch().size();
    }

    private int getFollowing(Long userId) {
        return em.selectFrom(follow)
                .where(follow.followEmbedded.userId.eq(userId))
                .fetch().size();
    }
}
