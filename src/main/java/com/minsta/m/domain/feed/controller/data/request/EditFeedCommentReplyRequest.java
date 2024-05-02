package com.minsta.m.domain.feed.controller.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditFeedCommentReplyRequest {

    @NotEmpty(message = "content is necessary value")
    private String content;
}
