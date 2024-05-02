package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsResponse {

    private Long leelsId;

    private UserResponse author;

    private String content;

    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    private String leelsUrl;

    private int heartCount;
}
