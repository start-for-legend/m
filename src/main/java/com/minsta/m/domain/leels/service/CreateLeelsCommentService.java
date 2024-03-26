package com.minsta.m.domain.leels.service;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;

public interface CreateLeelsCommentService {

    void execute(CreateLeelsCommentRequest createLeelsCommentRequest, Long leelsId);
}
