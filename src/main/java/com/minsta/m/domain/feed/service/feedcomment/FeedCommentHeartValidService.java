package com.minsta.m.domain.feed.service.feedcomment;

import com.minsta.m.global.entity.HeartValidResponse;

public interface FeedCommentHeartValidService {

    HeartValidResponse execute(Long feedId, Long feedCommentId);
}
