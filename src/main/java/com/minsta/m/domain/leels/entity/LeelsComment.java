package com.minsta.m.domain.leels.entity;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeelsComment extends BaseEntity {

    @Id
    @Column(name = "leels_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leelsCommentId;

    @ManyToOne
    @JoinColumn(name = "leels_id")
    private Leels leels;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String comment;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "leelsComment")
    List<LeelsCommentReply> leelsCommentReplies = new ArrayList<>();

    public void setComment(String comment) {
        this.comment = comment;
    }
}
