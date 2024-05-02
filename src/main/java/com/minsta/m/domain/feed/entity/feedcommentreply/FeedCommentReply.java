package com.minsta.m.domain.feed.entity.feedcommentreply;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FeedCommentReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_comment_reply_id", nullable = false)
    private Long feedCommentReplyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "feed")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "feed_comment_id")
    private FeedComment feedComment;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }
}
