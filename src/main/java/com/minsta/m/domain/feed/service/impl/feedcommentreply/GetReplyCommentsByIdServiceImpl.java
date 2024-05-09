package com.minsta.m.domain.feed.service.impl.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.response.FeedReplyCommentResponse;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReply;
import com.minsta.m.domain.feed.service.feedcommentreply.GetReplyCommentsByIdService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.minsta.m.domain.feed.entity.feedcommentreply.QFeedCommentReply.feedCommentReply;
import static com.minsta.m.domain.feed.entity.feedcommentreply.QFeedCommentReplyLike.feedCommentReplyLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetReplyCommentsByIdServiceImpl implements GetReplyCommentsByIdService {

    private final JPAQueryFactory em;

    @Override
    public List<FeedReplyCommentResponse> execute(Long feedId, Long feedCommentId, Long lastFeedReplyCommentId) {
        List<FeedCommentReply> feedComments = em
                .selectFrom(feedCommentReply)
                .where(feedCommentReply.feedComment.feedCommentId.eq(feedCommentId))
                .where(feedCommentReply.feedCommentReplyId.gt(lastFeedReplyCommentId))
                .orderBy(feedCommentReply.feedCommentReplyId.asc())
                .limit(5).stream().toList();

        return feedComments.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private FeedReplyCommentResponse convert(FeedCommentReply feedCommentReply) {

        boolean check = feedCommentReply.getUpdatedAt() != null && feedCommentReply.getUpdatedAt().isAfter(feedCommentReply.getCreatedAt());

        return FeedReplyCommentResponse.of(
                feedCommentReply.getFeedCommentReplyId(),
                getUser(feedCommentReply.getUser()),
                feedCommentReply.getContent(),
                getHeartCount(feedCommentReply.getFeedCommentReplyId()),
                check
        );
    }

    private int getHeartCount(Long id) {
        return em
                .selectFrom(feedCommentReplyLike)
                .where(feedCommentReplyLike.feedCommentReplyLikeEmbedded.feedCommentReplyId.eq(id))
                .fetch().size();
    }

    private UserResponse getUser(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getProfileUrl(), user.getName());
    }
}
