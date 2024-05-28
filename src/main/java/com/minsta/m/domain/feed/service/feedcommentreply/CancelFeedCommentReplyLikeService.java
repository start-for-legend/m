package com.minsta.m.domain.feed.service.feedcommentreply;

public interface CancelFeedCommentReplyLikeService {

    void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId);
}
