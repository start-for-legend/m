package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.response.UserDetailResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.domain.user.service.GetUserDetailService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.minsta.m.domain.feed.entity.feed.QFeed.feed;
import static com.minsta.m.domain.follow.entity.QFollow.follow;
import static com.minsta.m.domain.leels.entity.QLeels.leels;

@ReadOnlyService
@RequiredArgsConstructor
public class GetUserDetailServiceImpl implements GetUserDetailService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final JPAQueryFactory em;

    @Override
    public UserDetailResponse execute(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new BasicException(ErrorCode.USER_NOT_FOUND));
        List<Map<Long, String>> feeds = getFeedsList(user.getUserId());

        return UserDetailResponse.of(
                user.getUserId(),
                user.getNickName(),
                feeds.size(),
                getFollower(user.getUserId()),
                getFollowing(user.getUserId()),
                feeds,
                getLeelsList(user.getUserId()),
                user.getProfileUrl()
        );
    }

    private List<Map<Long, String>> getFeedsList(Long userId) {

        return em
                .select(feed.feedId, feed.fileUrls)
                .from(feed)
                .where(feed.user.userId.eq(userUtil.getUser().getUserId()))
                .orderBy(feed.createdAt.desc())
                .fetch()
                .stream()
                .map(tuple -> Map.of(tuple.get(feed.feedId), tuple.get(feed.fileUrls.get(0))))
                .collect(Collectors.toList());
    }

    private List<Map<Long, String>> getLeelsList(Long userId) {

        return em
                .select(leels.leelsId, leels.leelsUrl)
                .from(leels)
                .where(leels.user.userId.eq(userUtil.getUser().getUserId()))
                .orderBy(leels.createdAt.desc())
                .fetch()
                .stream()
                .map(tuple -> Map.of(tuple.get(leels.leelsId), tuple.get(leels.leelsUrl)))
                .collect(Collectors.toList());
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