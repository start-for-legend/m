package com.minsta.m.domain.feed.service.feedcommentreply;

public interface CancelLikeFeedCommentReplyService {

    void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId);
}
