package com.minsta.m.domain.main.controller.data.response;

import com.minsta.m.domain.feed.controller.data.response.FeedResponse;

import java.util.List;

public record MainResponse(
        List<MainStoryResponse> mainStoryResponses,
        List<FeedResponse> feedResponses
) {

    public static MainResponse of(List<MainStoryResponse> mainStoryResponses, List<FeedResponse> feedResponses) {
        return new MainResponse(mainStoryResponses, feedResponses);
    }
}
