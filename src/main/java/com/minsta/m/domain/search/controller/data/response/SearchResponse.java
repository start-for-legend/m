package com.minsta.m.domain.search.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;

import java.util.Map;

public record SearchResponse(
        Map<String, Long> hashTagResponses,
        Map<UserResponse, Long> userResponses
) {

    public static SearchResponse forHashTag(Map<String, Long> hashtags) {
        return new SearchResponse(hashtags, null);
    }

    public static SearchResponse forKeyword(Map<String, Long> hashtags, Map<UserResponse, Long> userResponses) {
        return new SearchResponse(hashtags, userResponses);
    }
}
