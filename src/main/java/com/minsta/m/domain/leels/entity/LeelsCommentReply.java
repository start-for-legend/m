package com.minsta.m.domain.leels.entity;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
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
public class LeelsCommentReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leels_comment_reply_id")
    private Long leelsCommentReplyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "leels_id")
    private Leels leels;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leels_comment_id")
    private LeelsComment leelsComment;

    @Column(nullable = false, length = 100)
    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }
}