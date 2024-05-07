package com.minsta.m.domain.feed.entity.feed;

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
public class FeedLike {

    @EmbeddedId
    private FeedLikeEmbedded feedLikeEmbedded;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private Feed feed;
}
