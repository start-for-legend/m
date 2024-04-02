package com.minsta.m.domain.feed.service.feedcomment;

public interface CommentCancelLikeService {

    void execute(Long feedId, Long feedCommentId);
}
