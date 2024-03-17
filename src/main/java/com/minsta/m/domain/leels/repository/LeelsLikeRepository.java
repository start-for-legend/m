package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import com.minsta.m.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeelsLikeRepository extends JpaRepository<LeelsLike, LeelsLikeEmbedded> {

    int countByUser(User user);

    boolean existsByLikeEmbedded(LeelsLikeEmbedded likeEmbedded);

    LeelsLike findByLikeEmbedded(LeelsLikeEmbedded likeEmbedded);
}
