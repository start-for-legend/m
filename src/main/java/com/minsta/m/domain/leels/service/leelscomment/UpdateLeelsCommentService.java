package com.minsta.m.domain.leels.service.leelscomment;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;

public interface UpdateLeelsCommentService {

    void execute(Long leelsId, Long leelsCommentId, CreateLeelsCommentRequest updateCommentRequest);
}
