package com.minsta.m.domain.feed.service.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentReplyRequest;

public interface CreateFeedCommentReplyService {

    void execute(Long feedId, Long feedCommentId, CreateFeedCommentReplyRequest createFeedCommentReplyRequest);
}
