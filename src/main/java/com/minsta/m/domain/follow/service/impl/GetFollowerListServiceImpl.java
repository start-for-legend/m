package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.domain.follow.service.GetFollowerListService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.minsta.m.domain.follow.entity.QFollow.follow;

@ReadOnlyService
@RequiredArgsConstructor
public class GetFollowerListServiceImpl implements GetFollowerListService {

    private final JPAQueryFactory em;

    @Override
    public List<UserResponse> execute(Long userId, Long lastUserId) {

        return em
                .selectFrom(follow)
                .where(follow.followEmbedded.followerId.eq(userId))
                .where(follow.followEmbedded.userId.gt(lastUserId))
                .orderBy(follow.followEmbedded.followerId.asc())
                .limit(15)
                .fetch()
                .stream()
                .map(r -> convert(r.getUser()))
                .toList();
    }

    private UserResponse convert(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getProfileUrl(), user.getName());
    }
}
