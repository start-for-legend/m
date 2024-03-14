package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeelsLikeRepository extends JpaRepository<LeelsLike, LeelsLikeEmbedded> {

    boolean existsByLikeEmbedded(LeelsLikeEmbedded likeEmbedded);
}
