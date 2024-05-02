package com.minsta.m.domain.leels.service.leelscomment;

import com.minsta.m.global.entity.HeartValidResponse;

public interface LeelsCommentHeartValidService {

    HeartValidResponse execute(Long leelsId, Long leelsCommentId);
}
