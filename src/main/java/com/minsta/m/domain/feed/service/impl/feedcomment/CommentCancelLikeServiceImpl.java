package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLike;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentLikeRepository;
import com.minsta.m.domain.feed.service.feedcomment.CommentCancelLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CommentCancelLikeServiceImpl implements CommentCancelLikeService {

    private final UserUtil userUtil;
    private final FeedCommentLikeRepository feedCommentLikeRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId) {
        FeedCommentLike feedCommentLike = feedCommentLikeRepository.findById(new FeedCommentLikeEmbedded(
                userUtil.getUser().getUserId(),
                feedId,
                feedCommentId
        )).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_LIKE));

        feedCommentLikeRepository.delete(feedCommentLike);
    }
}
