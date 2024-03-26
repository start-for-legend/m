package com.minsta.m.domain.user.controller.data.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String nickName,
        String profileUrl
) {}
