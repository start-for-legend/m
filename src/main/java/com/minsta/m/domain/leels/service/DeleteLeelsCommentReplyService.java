package com.minsta.m.domain.leels.service;

public interface DeleteLeelsCommentReplyService {

    void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId);
}
