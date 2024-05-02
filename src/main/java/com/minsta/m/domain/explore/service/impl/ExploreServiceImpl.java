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
import java.util.List;
import java.util.stream.Collectors;

import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;
import static com.minsta.m.domain.user.entity.QUser.user;
import static com.minsta.m.domain.feed.entity.feed.QFeedLike.feedLike;

@ReadOnlyService
@RequiredArgsConstructor
public class ExploreServiceImpl implements ExploreService {

    private final JPAQueryFactory em;
    private final GetReelsRecommendedService getReelsRecommendedService;

    @Override
    public ExploreResponse execute(int lastFeedId) {

        var query = em
                .selectFrom(feed)
                .leftJoin(feedLike).on(feedLike.feed.feedId.eq(feed.feedId))
                .leftJoin(user).on(feed.user.userId.eq(user.userId))
                .where(feed.feedId.gt(lastFeedId))
                .groupBy(feed.feedId)
                .orderBy(feedLike.count().desc())
                .limit(15);

        List<FeedResponse> results = query.fetch().stream()
                .map(e -> {
                    Long feedId = e.getFeedId();
                    Long userId = e.getUser().getUserId();
                    String nickName = e.getUser().getNickName();
                    String profileUrl = e.getUser().getProfileUrl();
                    String name = e.getUser().getName();
                    String content = e.getContent();
                    List<String> hashtags = e.getHashtags();
                    List<String> fileUrls = e.getFileUrls();

                    UserResponse userResponse = new UserResponse(userId, nickName, profileUrl, name);

                    return new FeedResponse(feedId, userResponse, content, hashtags, fileUrls);
                })
                .toList();

        var response = getReelsRecommendedService.execute();
        if (response.size() > 5) {
            response = response.subList(0, 5);
        }

        return ExploreResponse.of(results, response);
    }
}
