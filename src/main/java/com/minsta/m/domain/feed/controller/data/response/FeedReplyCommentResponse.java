package com.minsta.m.domain.feed.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;

public record FeedReplyCommentResponse(
    Long feedCommentReplyId,
    UserResponse user,
    String content,
    int heartCount,
    boolean modify
) {

    public static FeedReplyCommentResponse of(Long feedCommentReplyId, UserResponse user, String content, int heartCount, boolean modify) {
        return new FeedReplyCommentResponse(feedCommentReplyId, user, content, heartCount, modify);
    }

    public FeedReplyCommentResponse {}
}
