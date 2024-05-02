package com.minsta.m.domain.feed.entity.feedcommentreply;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
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
public class FeedCommentReplyLike {

    @EmbeddedId
    private FeedCommentReplyLikeEmbedded feedCommentReplyLikeEmbedded;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne
    @MapsId("feedCommentId")
    @JoinColumn(name = "feed_comment_id")
    private FeedComment feedComment;
}
