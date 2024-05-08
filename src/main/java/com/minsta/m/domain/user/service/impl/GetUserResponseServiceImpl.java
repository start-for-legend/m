package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.response.FeedMapResponse;
import com.minsta.m.domain.user.controller.data.response.LeelsMapResponse;
import com.minsta.m.domain.user.controller.data.response.UserDetailResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.service.GetUserResponseService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;
import static com.minsta.m.domain.follow.entity.QFollow.follow;
import static com.minsta.m.domain.leels.entity.QLeels.leels;

@ReadOnlyService
@RequiredArgsConstructor
public class GetUserResponseServiceImpl implements GetUserResponseService {

    private final UserUtil userUtil;
    private final JPAQueryFactory em;

    @Override
    public UserDetailResponse execute() {

        User user = userUtil.getUser();

        return UserDetailResponse.of(
                user.getUserId(),
                user.getName(),
                user.getNickName(),
                0,
                getFollower(user.getUserId()),
                getFollowing(user.getUserId()),
                getFeedsList(),
                getLeelsList(),
                user.getProfileUrl()
        );
    }

    private List<FeedMapResponse> getFeedsList() {
        return em
                .select(feed.feedId, feed.fileUrls)
                .from(feed)
                .where(feed.user.userId.eq(userUtil.getUser().getUserId()))
                .orderBy(feed.createdAt.desc())
                .fetch()
                .stream()
                .map(tuple -> {
                    List<Object> list = Collections.singletonList(tuple.get(feed.fileUrls));
                    String firstUrl = (String) list.get(0);
                    return FeedMapResponse.of(tuple.get(feed.feedId), firstUrl);
                })
                .toList();
    }

    private List<LeelsMapResponse> getLeelsList() {

        return em
                .select(leels.leelsId, leels.leelsUrl)
                .from(leels)
                .where(leels.user.userId.eq(userUtil.getUser().getUserId()))
                .orderBy(leels.createdAt.desc())
                .fetch()
                .stream()
                .map(tuple -> LeelsMapResponse.of(tuple.get(leels.leelsId), tuple.get(leels.leelsUrl)))
                .toList();
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
