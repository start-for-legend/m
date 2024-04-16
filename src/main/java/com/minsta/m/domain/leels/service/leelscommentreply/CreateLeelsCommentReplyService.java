package com.minsta.m.domain.leels.service.leelscommentreply;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;

public interface CreateLeelsCommentReplyService {

    void execute(Long leelsId, Long leelsCommentId, CreateLeelsCommentRequest replyRequest);
}
