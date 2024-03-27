package com.minsta.m.domain.follow.repository;

import com.minsta.m.domain.follow.entity.Follow;
import com.minsta.m.domain.follow.entity.FollowEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowEmbedded> {

    Follow findByFollowEmbedded(FollowEmbedded followEmbedded);
}
