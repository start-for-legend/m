package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentReplyRepository extends JpaRepository<FeedCommentReply, Long> {
}
