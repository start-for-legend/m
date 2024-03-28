package com.minsta.m.domain.story.controller.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStoryRequest {

    @NotEmpty(message = "url field is necessary")
    private String url;
}

