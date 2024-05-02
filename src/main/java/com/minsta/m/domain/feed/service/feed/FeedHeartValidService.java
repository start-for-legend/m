package com.minsta.m.domain.feed.service.feed;

import com.minsta.m.global.entity.HeartValidResponse;

public interface FeedHeartValidService {

    HeartValidResponse execute(Long feedId);
}
