package com.minsta.m.domain.feed.service;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedRequest;

public interface CreateFeedService {

    void execute(CreateFeedRequest createFeedRequest);
}
