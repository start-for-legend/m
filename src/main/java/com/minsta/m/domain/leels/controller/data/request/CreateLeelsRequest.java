package com.minsta.m.domain.leels.controller.data.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeelsRequest {

    private String content;

    private List<String> hashtags;

    @NotNull(message = "url value is necessary")
    private String url;
}
