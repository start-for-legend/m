package com.minsta.m.domain.explore.controller.response;

import com.minsta.m.domain.feed.controller.data.response.FeedResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;

import java.util.List;

public record ExploreResponse(
        List<FeedResponse> feedResponse,
        List<LeelsResponse> leelsResponse
) {

    public static ExploreResponse of(List<FeedResponse> feedResponse, List<LeelsResponse> leelsResponse) {
        return new ExploreResponse(feedResponse, leelsResponse);
    }

    public ExploreResponse {}
}
