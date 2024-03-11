package com.minsta.m.domain.leels.controller.data.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeelsCommentRequest {

    @NotNull(message = "comment is necessary value")
    private String comment;
}
