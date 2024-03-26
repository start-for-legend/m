package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsResponse {

    private Long leelsId;

    private User author;

    private String content;

    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    private String leelsUrl;

    @Builder.Default
    private List<LeelsCommentResponse> leelsCommentResponses = new ArrayList<>();

    private int heartCount;
}
