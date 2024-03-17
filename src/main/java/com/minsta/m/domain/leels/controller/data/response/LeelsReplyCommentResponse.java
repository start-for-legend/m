package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsReplyCommentResponse {

    private Long leelsReplyCommentId;

    private User author;

    private String comment;

    private int heartCount;

    private boolean modify;
}
