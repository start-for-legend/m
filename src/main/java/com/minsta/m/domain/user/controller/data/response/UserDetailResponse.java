package com.minsta.m.domain.user.controller.data.response;

import java.util.List;
import java.util.Map;

public record UserDetailResponse(
        Long userId,
        String nickName,
        int countPost,
        int follower,
        int following,
        List<?> feeds,
        List<Map<Long, String>> leels,
        String profileUrl
) {

    public static UserDetailResponse of(
            Long userId,
            String nickName,
            int countPost,
            int follower,
            int following,
            List<?> feeds,
            List<Map<Long, String>> leels,
            String profileUrl
    ) {
        return new UserDetailResponse(userId, nickName, countPost, follower, following, feeds, leels, profileUrl);
    }

    public UserDetailResponse {}
}
