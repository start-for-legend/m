package com.minsta.m.domain.leels.service.leelscomment;

import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;

import java.util.List;

public interface GetCommentListByLeelsIdService {

    List<LeelsCommentResponse> execute(Long leelsId, Long lastCommentId);
}
