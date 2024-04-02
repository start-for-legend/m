package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLike;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLikeEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentLikeRepository extends JpaRepository<FeedCommentLike, FeedCommentLikeEmbedded> {
}
