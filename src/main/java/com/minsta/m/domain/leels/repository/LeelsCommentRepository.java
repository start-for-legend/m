package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.LeelsComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeelsCommentRepository extends JpaRepository<LeelsComment, Long> {
}