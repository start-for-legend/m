package com.minsta.m.domain.explore.service.impl;

import com.minsta.m.domain.explore.controller.response.ExploreResponse;
import com.minsta.m.domain.explore.service.ExploreService;
import com.minsta.m.domain.feed.controller.data.response.FeedResponse;
import com.minsta.m.domain.leels.service.leels.GetReelsRecommendedService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;
import static com.minsta.m.domain.user.entity.QUser.user;
import static com.minsta.m.domain.feed.entity.feed.QFeedLike.feedLike;

@ReadOnlyService
@RequiredArgsConstructor
public class ExploreServiceImpl implements ExploreService {

    private final JPAQueryFactory em;
    private final GetReelsRecommendedService getReelsRecommendedService;

    @Override
    public ExploreResponse execute(int page) {

        var res = em
                .select(Projections.constructor(
                        FeedResponse.class,
                        feed.feedId,
                        Projections.constructor(UserResponse.class, // 중첩 Projections
                                user.userId,
                                user.nickName,
                                user.profileUrl,
                                user.name),
                        feed.content,
                        Expressions.list(feed.hashtags),
                        Expressions.list(feed.fileUrls)
                ))
                .from(feed)
                .leftJoin(feedLike).on(feedLike.feed.feedId.eq(feed.feedId))
                .leftJoin(user).on(feed.user.userId.eq(user.userId))
                .groupBy(feed.feedId)
                .orderBy(feedLike.count().desc())
                .limit(15)
                .offset((page - 1) * 15L)
                .fetch();

        var response = getReelsRecommendedService.execute();

        return ExploreResponse.of(res, new ArrayList<>(response.subList(5, response.size())));
    }
}
