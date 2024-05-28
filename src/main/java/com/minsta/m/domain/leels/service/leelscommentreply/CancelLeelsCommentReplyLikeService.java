package com.minsta.m.domain.leels.service.leelscommentreply;

public interface CancelLeelsCommentReplyLikeService {

    void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId);
}
