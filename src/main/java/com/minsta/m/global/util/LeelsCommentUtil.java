package com.minsta.m.global.util;

import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.exception.LeelsCommentNotFoundException;
import com.minsta.m.domain.leels.repository.LeelsCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeelsCommentUtil {

    private final LeelsCommentRepository leelsCommentRepository;

    public LeelsComment getComment(Long leelsCommentId) {
        return leelsCommentRepository.findById(leelsCommentId).orElseThrow(LeelsCommentNotFoundException::new);
    }
}
