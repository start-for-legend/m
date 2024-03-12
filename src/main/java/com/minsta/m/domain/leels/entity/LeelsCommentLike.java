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
public class LeelsCommentLike {

    @EmbeddedId
    private LeelsCommentEmbedded leelsCommentEmbedded;

    @ManyToOne
    @MapsId("user_id")
    private User user;

    @ManyToOne
    @MapsId("leels_id")
    private Leels leels;

    @ManyToOne
    @MapsId("leels_comment_id")
    private LeelsComment leelsComment;
}
