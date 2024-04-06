package com.minsta.m.domain.feed.service.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentReplyRequest;

public interface EditFeedCommentReplyService {

    void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId, EditFeedCommentReplyRequest editFeedCommentReplyRequest);
}
