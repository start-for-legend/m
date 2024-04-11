package com.minsta.m.domain.feed.repository;

import com.minsta.m.domain.feed.entity.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findAllByHashtagsContains(String hash);
}
