package com.minsta.m.domain.user.controller.data.request;

import jakarta.validation.constraints.NotEmpty;

public record AwsUrlRequest(
        @NotEmpty(message = "url is necessary field")
        String awsUrl
) {}
