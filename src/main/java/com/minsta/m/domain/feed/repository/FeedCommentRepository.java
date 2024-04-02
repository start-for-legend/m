package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
}
