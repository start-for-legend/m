package com.minsta.m.domain.leels.controller.data.response;

import com.minsta.m.domain.user.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LeelsCommentResponse {

    private Long leelsCommentId;

    private User author;

    private String comment;

    private int heartCount;

    private List<LeelsReplyCommentResponse> leelsReplyCommentResponses = new ArrayList<>();

    private boolean modify;
}
