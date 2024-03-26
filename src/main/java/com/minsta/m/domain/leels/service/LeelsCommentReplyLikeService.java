package com.minsta.m.domain.leels.service;

public interface LeelsCommentReplyLikeService {

    void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId);
}
