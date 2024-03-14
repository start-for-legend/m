package com.minsta.m.global.util;

import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.exception.LeelsCommentNotFoundException;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeelsCommentReplyUtil {

    private final LeelsCommentReplyRepository leelsCommentReplyRepository;

    public LeelsCommentReply getReply(Long commentReplyId) {
        return leelsCommentReplyRepository.findById(commentReplyId).orElseThrow(LeelsCommentNotFoundException::new);
    }
}
