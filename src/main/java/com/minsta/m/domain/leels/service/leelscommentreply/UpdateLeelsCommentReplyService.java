package com.minsta.m.domain.leels.service.leelscommentreply;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;

public interface UpdateLeelsCommentReplyService {

    void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId, CreateLeelsCommentRequest createLeelsCommentRequest);
}
