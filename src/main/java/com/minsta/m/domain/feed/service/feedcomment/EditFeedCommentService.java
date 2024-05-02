package com.minsta.m.domain.feed.service.feedcomment;

import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentRequest;

public interface EditFeedCommentService {

    void execute(Long feedId, Long feedCommentId, EditFeedCommentRequest editFeedCommentRequest);
}
