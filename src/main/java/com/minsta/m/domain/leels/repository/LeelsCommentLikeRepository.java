package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.LeelsCommentEmbedded;
import com.minsta.m.domain.leels.entity.LeelsCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeelsCommentLikeRepository extends JpaRepository<LeelsCommentLike, LeelsCommentEmbedded> {
}
