package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsCommentResponse {

    private Long leelsCommentId;

    private UserResponse author;

    private String comment;

    private int heartCount;

    @Builder.Default
    private List<LeelsReplyCommentResponse> leelsReplyCommentResponses = new ArrayList<>();

    private boolean modify;
}
