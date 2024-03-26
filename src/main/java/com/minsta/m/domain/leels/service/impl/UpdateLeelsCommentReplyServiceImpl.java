package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.exception.CommentUpdatePermissionDeniedException;
import com.minsta.m.domain.leels.exception.LeelsCommentNotFoundException;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyRepository;
import com.minsta.m.domain.leels.service.UpdateLeelsCommentReplyService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class UpdateLeelsCommentReplyServiceImpl implements UpdateLeelsCommentReplyService {

    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsUtil leelsUtil;
    private final UserUtil userUtil;
    private final LeelsCommentReplyRepository leelsCommentReplyRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId, CreateLeelsCommentRequest createLeelsCommentRequest) {

        LeelsCommentReply leelsCommentReply = leelsCommentReplyRepository.findById(leelsCommentReplyId).orElseThrow(LeelsCommentNotFoundException::new);
        if (!leelsCommentReply.getUser().equals(userUtil.getUser())) {
            throw new CommentUpdatePermissionDeniedException();
        }

        if (!leelsCommentReply.getLeels().equals(leelsUtil.getLeels(leelsId))) {
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        if (!leelsCommentReply.getLeelsComment().equals(leelsCommentUtil.getComment(leelsCommentId))) {
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        leelsCommentReply.setComment(createLeelsCommentRequest.getComment());
        leelsCommentReplyRepository.save(leelsCommentReply);
    }
}
