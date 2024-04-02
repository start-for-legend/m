package com.minsta.m.domain.feed.service.feedcomment;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentRequest;

public interface CreateFeedCommentService {

    void execute(CreateFeedCommentRequest createFeedCommentRequest, Long feedId);
}
