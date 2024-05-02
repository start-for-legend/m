package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.controller.data.response.UserResponse;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsCommentResponse {

    private Long leelsCommentId;

    private UserResponse author;

    private String comment;

    private int heartCount;

    private boolean modify;
}
