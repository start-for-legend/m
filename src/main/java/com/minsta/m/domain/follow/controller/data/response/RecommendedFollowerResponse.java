package com.minsta.m.domain.follow.controller.data.response;

import lombok.Builder;

@Builder
public record RecommendedFollowerResponse (
        Long userId,
        String profileUrl,
        String nickName
){ }
