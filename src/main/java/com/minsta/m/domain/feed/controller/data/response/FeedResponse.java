package com.minsta.m.domain.feed.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {

    private Long feedId;

    private UserResponse userResponse;

    private String content;

    private List<String> hashtags = new ArrayList<>();

    private List<String> fileUrls = new ArrayList<>();

}
