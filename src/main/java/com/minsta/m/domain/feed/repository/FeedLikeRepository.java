package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feed.FeedLike;
import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeEmbedded> {
}
