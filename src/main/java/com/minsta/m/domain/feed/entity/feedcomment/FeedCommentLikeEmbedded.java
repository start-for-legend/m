package com.minsta.m.domain.feed.entity.feedcomment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FeedCommentLikeEmbedded implements Serializable {

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "feed_comment_id")
    private Long feedCommentId;
}
