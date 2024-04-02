package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.service.feedcomment.DeleteFeedCommentService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class DeleteFeedCommentServiceImpl implements DeleteFeedCommentService {

    private final UserUtil userUtil;
    private final FeedCommentRepository feedCommentRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId) {

        FeedComment comment = feedCommentRepository.findById(feedCommentId).orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_NOT_FOUNND));
        if (!comment.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.FEED_COMMENT_NOT_OWNER);
        }

        feedCommentRepository.delete(comment);
    }
}
