package com.minsta.m.domain.user.controller.data.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String nickName,
        String profileUrl,
        int follower,
        int following
) {
    public static UserResponse of(Long userId, String nickName, String profileUrl) {
        return new UserResponse(userId, nickName, profileUrl, 0, 0);
    }
}
