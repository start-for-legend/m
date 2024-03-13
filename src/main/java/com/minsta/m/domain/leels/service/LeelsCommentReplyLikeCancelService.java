package com.minsta.m.domain.leels.service;

public interface LeelsCommentReplyLikeCancelService {

    void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId);
}
