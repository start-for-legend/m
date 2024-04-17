package com.minsta.m.domain.feed.service.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.response.FeedReplyCommentResponse;

import java.util.List;

public interface GetReplyCommentsByIdService {

    List<FeedReplyCommentResponse> execute(Long feedId, Long feedCommentId, Long lastFeedReplyCommentId);
}
