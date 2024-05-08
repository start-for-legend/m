package com.minsta.m.domain.user.controller.data.response;

import java.util.List;
import java.util.Map;

public record UserDetailResponse(
        Long userId,
        String name,
        String nickName,
        int countPost,
        int follower,
        int following,
        List<FeedMapResponse> feeds,
        List<LeelsMapResponse> leels,
        String profileUrl
) {

    public static UserDetailResponse of(
            Long userId,
            String name,
            String nickName,
            int countPost,
            int follower,
            int following,
            List<FeedMapResponse> feeds,
            List<LeelsMapResponse> leels,
            String profileUrl
    ) {
        return new UserDetailResponse(userId, name, nickName, countPost, follower, following, feeds, leels, profileUrl);
    }

    public UserDetailResponse {}
}
