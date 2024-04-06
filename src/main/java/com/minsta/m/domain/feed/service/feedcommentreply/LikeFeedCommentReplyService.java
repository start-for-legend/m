package com.minsta.m.domain.feed.service.feedcommentreply;

public interface LikeFeedCommentReplyService {

    void execute(Long feedId, Long feedCommentId, Long feedCommentReplyId);
}
