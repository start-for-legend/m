package com.minsta.m.domain.story.controller.data.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record StoryResponse(
        String url,
        List<Long> watchers,
        LocalDateTime createdAt
) {

    public static StoryResponse ownerOf(String url, List<Long> user, LocalDateTime createdAt) {
        return new StoryResponse(url, user, createdAt);
    }

    public static StoryResponse notOwnerOf(String url, LocalDateTime createdAt) {
        return new StoryResponse(url, new ArrayList<>(), createdAt);
    }

    public StoryResponse {}
}
