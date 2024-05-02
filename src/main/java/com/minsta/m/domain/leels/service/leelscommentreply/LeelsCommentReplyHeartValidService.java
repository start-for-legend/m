package com.minsta.m.domain.leels.service.leelscommentreply;

import com.minsta.m.global.entity.HeartValidResponse;

public interface LeelsCommentReplyHeartValidService {

    HeartValidResponse execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId);
}
