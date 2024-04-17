package com.minsta.m.domain.feed.service.feedcomment;

import com.minsta.m.domain.feed.controller.data.response.FeedCommentResponse;

import java.util.List;

public interface GetCommentsByIdService {

    List<FeedCommentResponse> execute(Long feedId, Long lastFeedCommentId);
}
