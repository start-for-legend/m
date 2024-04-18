package com.minsta.m.domain.main.controller.data.response;

import java.util.List;
import java.util.Map;

public record MainStoryResponse(
        long userId,
        String profileUrl,
        String nickName,
        List<Map<Long, Boolean>> storyValue
) {

    public static MainStoryResponse of(long userId, String profileUrl, String nickName, List<Map<Long, Boolean>> storyValue) {
        return new MainStoryResponse(userId, profileUrl, nickName, storyValue);
    }

    public MainStoryResponse {}
}
