package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
