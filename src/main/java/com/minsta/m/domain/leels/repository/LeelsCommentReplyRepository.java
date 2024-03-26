package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeelsCommentReplyRepository extends JpaRepository<LeelsCommentReply, Long> {

    List<LeelsCommentReply> findAllByLeelsAndLeelsComment(Leels leels, LeelsComment leelsComment);
}
