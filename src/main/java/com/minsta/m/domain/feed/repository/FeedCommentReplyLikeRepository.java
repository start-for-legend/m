package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReplyLike;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReplyLikeEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentReplyLikeRepository extends JpaRepository<FeedCommentReplyLike, FeedCommentReplyLikeEmbedded> {
}
