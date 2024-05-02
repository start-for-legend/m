package com.minsta.m.domain.user.controller.data.response;

public record LeelsMapResponse (
        Long leelsId,
        String leelsUrl
) {

    public static LeelsMapResponse of(Long leelsId, String leelsUrl) {
        return new LeelsMapResponse(leelsId, leelsUrl);
    }

    public LeelsMapResponse {}
}
