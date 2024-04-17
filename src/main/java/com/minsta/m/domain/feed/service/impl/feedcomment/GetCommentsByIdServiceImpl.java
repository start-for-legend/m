package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.controller.data.response.FeedCommentResponse;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.service.feedcomment.GetCommentsByIdService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.minsta.m.domain.feed.entity.feedcomment.QFeedComment.feedComment;
import static com.minsta.m.domain.feed.entity.feedcomment.QFeedCommentLike.feedCommentLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetCommentsByIdServiceImpl implements GetCommentsByIdService {

    private final JPAQueryFactory em;

    @Override
    public List<FeedCommentResponse> execute(Long feedId, Long lastFeedCommentId) {

        List<FeedComment> feedComments = em
                .selectFrom(feedComment)
                .where(feedComment.feed.feedId.eq(feedId))
                .where(feedComment.feedCommentId.gt(lastFeedCommentId))
                .orderBy(feedComment.feedCommentId.asc())
                .limit(5).stream().toList();

        return feedComments.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private FeedCommentResponse convert(FeedComment feedComment) {

        boolean check = feedComment.getUpdatedAt() != null && feedComment.getUpdatedAt().isAfter(feedComment.getCreatedAt());

        return FeedCommentResponse.of(
                feedComment.getFeedCommentId(),
                getUser(feedComment.getUser()),
                feedComment.getContent(),
                getHeartCount(feedComment.getFeedCommentId()),
                check
        );
    }

    private int getHeartCount(Long id) {
        return em
                .selectFrom(feedCommentLike)
                .where(feedCommentLike.feedCommentLikeEmbedded.feedCommentId.eq(id))
                .fetch().size();
    }

    private UserResponse getUser(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getNickName(), user.getProfileUrl());
    }
}
