package com.minsta.m.domain.feed.service.feed;

import com.minsta.m.domain.feed.controller.data.response.FeedResponse;

public interface GetFeedDetailService {

    FeedResponse execute(Long feedId);
}
