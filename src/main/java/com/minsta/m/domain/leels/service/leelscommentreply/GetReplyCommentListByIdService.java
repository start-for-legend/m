package com.minsta.m.domain.leels.service.leelscommentreply;

import com.minsta.m.domain.leels.controller.data.response.LeelsReplyCommentResponse;

import java.util.List;

public interface GetReplyCommentListByIdService {

    List<LeelsReplyCommentResponse> execute(Long leelsCommentId, Long lastReplyCommentId);
}
