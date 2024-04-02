package com.minsta.m.domain.feed.entity.feedcomment;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FeedComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feed_comment_id")
    private Long feedCommentId;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feed")
    private Feed feed;

    public void setContent(String content) {
        this.content = content;
    }
}
