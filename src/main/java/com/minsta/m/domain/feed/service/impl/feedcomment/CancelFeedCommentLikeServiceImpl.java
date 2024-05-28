package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLike;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentLikeRepository;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feedcomment.CancelFeedCommentLikeService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelFeedCommentLikeServiceImpl implements CancelFeedCommentLikeService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentLikeRepository feedCommentLikeRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId) {

        User currentUser = userUtil.getUser();
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));
        FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_NOT_FOUND));


        FeedCommentLike feedCommentLike = feedCommentLikeRepository.findById(new FeedCommentLikeEmbedded(
                currentUser.getUserId(),
                feed.getFeedId(),
                feedComment.getFeedCommentId()
        )).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_LIKE));

        feedCommentLikeRepository.delete(feedCommentLike);
    }
}
