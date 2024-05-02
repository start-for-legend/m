package com.minsta.m.domain.feed.service.impl.feedcommentreply;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReplyLike;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReplyLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentReplyLikeRepository;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feedcommentreply.CancelLikeFeedCommentReplyService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelLikeFeedCommentReplyServiceImpl implements CancelLikeFeedCommentReplyService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentReplyLikeRepository feedCommentReplyLikeRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId) {

        User currentUser = userUtil.getUser();
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));
        FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_NOT_FOUND));

        if (feedCommentReplyLikeRepository.existsById(new FeedCommentReplyLikeEmbedded(
                currentUser.getUserId(),
                feedId,
                feedCommentId,
                feedCommentReplyId
        ))) {
            throw new BasicException(ErrorCode.FEED_NOT_LIKE);
        }

        FeedCommentReplyLike feedCommentReplyLike = FeedCommentReplyLike.builder()
                .user(currentUser)
                .feed(feed)
                .feedComment(feedComment)
                .feedCommentReplyLikeEmbedded(new FeedCommentReplyLikeEmbedded(
                        currentUser.getUserId(),
                        feedId,
                        feedCommentId,
                        feedCommentReplyId
                ))
                .build();

        feedCommentReplyLikeRepository.delete(feedCommentReplyLike);
    }
}
