package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.response.FeedMapResponse;
import com.minsta.m.domain.user.controller.data.response.LeelsMapResponse;
import com.minsta.m.domain.user.controller.data.response.UserDetailResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.domain.user.service.GetUserDetailService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;
import static com.minsta.m.domain.follow.entity.QFollow.follow;
import static com.minsta.m.domain.leels.entity.QLeels.leels;

@ReadOnlyService
@RequiredArgsConstructor
public class GetUserDetailServiceImpl implements GetUserDetailService {

    private final UserRepository userRepository;
    private final JPAQueryFactory em;

    @Override
    public UserDetailResponse execute(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new BasicException(ErrorCode.USER_NOT_FOUND));
        List<FeedMapResponse> feeds = getFeedsList(user.getUserId());

        return UserDetailResponse.of(
                user.getUserId(),
                user.getName(),
                user.getNickName(),
                feeds.size(),
                getFollower(user.getUserId()),
                getFollowing(user.getUserId()),
                feeds,
                getLeelsList(user.getUserId()),
                user.getProfileUrl()
        );
    }

    private List<FeedMapResponse> getFeedsList(Long userId) {
        return em
                .select(feed.feedId, feed.fileUrls)
                .from(feed)
                .where(feed.user.userId.eq(userId))
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

    private List<LeelsMapResponse> getLeelsList(Long userId) {

        return em
                .select(leels.leelsId, leels.leelsUrl)
                .from(leels)
                .where(leels.user.userId.eq(userId))
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
