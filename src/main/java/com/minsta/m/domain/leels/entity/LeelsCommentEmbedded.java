package com.minsta.m.domain.leels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LeelsCommentEmbedded implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "leels_id")
    private Long leelsId;

    @Column(name = "leels_comment_id")
    private Long leelsCommentId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LeelsCommentEmbedded that = (LeelsCommentEmbedded) obj;

        if (!Objects.equals(userId, that.userId)) return false;
        if (!Objects.equals(leelsId, that.leelsId)) return false;
        return Objects.equals(leelsCommentId, that.leelsCommentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, leelsId, leelsCommentId);
    }
}
