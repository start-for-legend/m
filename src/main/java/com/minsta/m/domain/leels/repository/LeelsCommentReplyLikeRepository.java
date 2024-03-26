package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.LeelsCommentReplyLike;
import com.minsta.m.domain.leels.entity.LeelsCommentReplyLikeEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeelsCommentReplyLikeRepository extends JpaRepository<LeelsCommentReplyLike, LeelsCommentReplyLikeEmbedded> {

    LeelsCommentReplyLike findByLeelsCommentReplyLikeEmbedded(LeelsCommentReplyLikeEmbedded leelsCommentReplyLikeEmbedded);
}
