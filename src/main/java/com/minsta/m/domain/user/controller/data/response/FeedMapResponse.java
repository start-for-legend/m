package com.minsta.m.domain.user.controller.data.response;

public record FeedMapResponse(
        Long feedId,
        String feedUrlOne
) {

    public static FeedMapResponse of(Long feedId, String feedUrlOne) {
        return new FeedMapResponse(feedId, feedUrlOne);
    }

    public FeedMapResponse {}
}
