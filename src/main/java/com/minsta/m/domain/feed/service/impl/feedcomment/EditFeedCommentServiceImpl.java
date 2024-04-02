package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentRequest;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.service.feedcomment.EditFeedCommentService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class EditFeedCommentServiceImpl implements EditFeedCommentService {

    private final UserUtil userUtil;
    private final FeedCommentRepository feedCommentRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId, EditFeedCommentRequest editFeedCommentRequest) {

        FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_NOT_FOUNND));
        if (!feedComment.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.FEED_COMMENT_NOT_OWNER);
        }

        feedComment.setContent(editFeedCommentRequest.getContent());
        feedCommentRepository.save(feedComment);
    }
}
