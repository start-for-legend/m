package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.exception.CommentDeletePermissionDeniedException;
import com.minsta.m.domain.leels.exception.LeelsCommentNotFoundException;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyRepository;
import com.minsta.m.domain.leels.service.DeleteLeelsCommentReplyService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class DeleteLeelsCommentReplyServiceImpl implements DeleteLeelsCommentReplyService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentReplyRepository leelsCommentReplyRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId) {
        LeelsCommentReply leelsCommentReply = leelsCommentReplyRepository.findById(leelsCommentReplyId)
                .orElseThrow(LeelsCommentNotFoundException::new);
        if (!leelsCommentReply.getUser().equals(userUtil.getUser())){
            throw new CommentDeletePermissionDeniedException();
        }

        if (!leelsCommentReply.getLeels().equals(leelsUtil.getLeels(leelsId))) {
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        if (!leelsCommentReply.getLeelsComment().equals(leelsCommentUtil.getComment(leelsCommentId))) {
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        leelsCommentReplyRepository.delete(leelsCommentReply);
    }
}
