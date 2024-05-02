package com.minsta.m.domain.leels.entity;

import com.minsta.m.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeelsCommentReplyLike {

    @EmbeddedId
    private LeelsCommentReplyLikeEmbedded leelsCommentReplyLikeEmbedded;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("leelsId")
    @JoinColumn(name = "leels_id")
    private Leels leels;

    @ManyToOne
    @MapsId("leelsCommentId")
    @JoinColumn(name = "leels_comment_id")
    private LeelsComment leelsComment;

    @ManyToOne
    @MapsId("leelsCommentReplyId")
    @JoinColumn(name = "leels_comment_reply_id")
    private LeelsCommentReply leelsCommentReply;
}
