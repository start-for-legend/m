package com.minsta.m.domain.feed.service.feedcomment;

public interface CancelFeedCommentLikeService {

    void execute(Long feedId, Long feedCommentId);
}
