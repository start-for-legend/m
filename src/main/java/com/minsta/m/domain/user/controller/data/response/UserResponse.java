package com.minsta.m.domain.user.controller.data.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String nickName,
        String profileUrl,
        int follower,
        int following,
        String name
) {

    public static UserResponse of(Long userId, String nickName, String profileUrl, String name) {
        return new UserResponse(userId, nickName, profileUrl, 0, 0, name);
    }
}
