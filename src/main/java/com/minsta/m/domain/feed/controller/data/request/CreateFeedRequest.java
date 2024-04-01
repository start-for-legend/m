package com.minsta.m.domain.feed.controller.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedRequest {

    private String content;

    private List<String> hashtags;

    @NotEmpty(message = "url value is necessary")
    private List<String> url;
}
