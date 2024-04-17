package com.minsta.m.domain.leels.service.impl.leelscomment;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.exception.CommentUpdatePermissionDeniedException;
import com.minsta.m.domain.leels.repository.LeelsCommentRepository;
import com.minsta.m.domain.leels.service.leelscomment.UpdateLeelsCommentService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class UpdateLeelsCommentServiceImpl implements UpdateLeelsCommentService {

    private final LeelsUtil leelsUtil;
    private final UserUtil userUtil;
    private final LeelsCommentRepository leelsCommentRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, CreateLeelsCommentRequest updateCommentRequest) {
        LeelsComment leelsComment = leelsCommentRepository.findByLeelsCommentIdAndLeels(leelsCommentId, leelsUtil.getLeels(leelsId));
        if (!leelsComment.getUser().equals(userUtil.getUser())) {
            throw new CommentUpdatePermissionDeniedException();
        }

        leelsComment.setComment(updateCommentRequest.getComment());
        leelsCommentRepository.save(leelsComment);
    }
}
