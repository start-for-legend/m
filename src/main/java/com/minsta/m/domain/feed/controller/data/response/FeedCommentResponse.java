package com.minsta.m.domain.feed.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;

public record FeedCommentResponse(
        Long feedCommentId,
        UserResponse user,
        String content,
        int heartCount,
        boolean modify
) {

    public static FeedCommentResponse of(Long feedCommentId, UserResponse user, String content, int heartCount, boolean modify) {
        return new FeedCommentResponse(feedCommentId, user, content, heartCount, modify);
    }
}
