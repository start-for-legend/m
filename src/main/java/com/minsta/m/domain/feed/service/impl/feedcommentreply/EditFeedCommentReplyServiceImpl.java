package com.minsta.m.domain.feed.service.impl.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentReplyRequest;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReply;
import com.minsta.m.domain.feed.repository.FeedCommentReplyRepository;
import com.minsta.m.domain.feed.service.feedcommentreply.EditFeedCommentReplyService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class EditFeedCommentReplyServiceImpl implements EditFeedCommentReplyService {

    private final UserUtil userUtil;
    private final FeedCommentReplyRepository feedCommentReplyRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId, EditFeedCommentReplyRequest editFeedCommentReplyRequest) {

        FeedCommentReply feedCommentReply = feedCommentReplyRepository.findById(feedCommentReplyId)
                .orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_REPLY_NOT_FOUND));
        if (!feedCommentReply.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.FEED_COMMENT_REPLY_NOT_OWNER);
        }

        feedCommentReply.setContent(editFeedCommentReplyRequest.getContent());
        feedCommentReplyRepository.save(feedCommentReply);
    }
}
