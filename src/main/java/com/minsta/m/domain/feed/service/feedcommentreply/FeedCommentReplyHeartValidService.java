package com.minsta.m.domain.feed.service.feedcommentreply;

import com.minsta.m.global.entity.HeartValidResponse;

public interface FeedCommentReplyHeartValidService {

    HeartValidResponse execute(Long feedId, Long feedCommentId, Long feedCommentReplyId);
}
