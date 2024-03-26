package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeelsCommentRepository extends JpaRepository<LeelsComment, Long> {

    List<LeelsComment> findAllByLeels(Leels leels);

    LeelsComment findByLeelsCommentIdAndLeels(Long leelsCommentId, Leels leels);
}
