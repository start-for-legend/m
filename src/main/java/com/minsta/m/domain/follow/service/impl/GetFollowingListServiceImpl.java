package com.minsta.m.domain.follow.service.impl;

import com.minsta.m.domain.follow.service.GetFollowingListService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.minsta.m.domain.follow.entity.QFollow.follow;

@ReadOnlyService
@RequiredArgsConstructor
public class GetFollowingListServiceImpl implements GetFollowingListService {

    private final JPAQueryFactory em;

    @Override
    public List<UserResponse> execute(Long userId, Long lastUserId) {

        return em
                .selectFrom(follow)
                .where(follow.followEmbedded.followerId.gt(lastUserId))
                .where(follow.followEmbedded.followerId.eq(userId))
                .orderBy(follow.followEmbedded.userId.asc())
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
