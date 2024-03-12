package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.exception.CommentDeletePermissionDeniedException;
import com.minsta.m.domain.leels.repository.LeelsCommentLikeRepository;
import com.minsta.m.domain.leels.repository.LeelsCommentRepository;
import com.minsta.m.domain.leels.service.LeelsCommentDeleteService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsCommentDeleteServiceImpl implements LeelsCommentDeleteService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentRepository leelsCommentRepository;
    private final LeelsCommentLikeRepository leelsCommentLikeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId) {
        User user = userUtil.getUser();

        LeelsComment leelsComment = leelsCommentRepository.findByLeelsCommentIdAndLeels(leelsCommentId, leelsUtil.getLeels(leelsId));
        if (!leelsComment.getUser().equals(user)) {
            throw new CommentDeletePermissionDeniedException();
        }

        leelsCommentRepository.delete(leelsComment);
    }

    private void deleteAllLike(Long leelsCommentId) {
        leelsCommentLikeRepository.deleteAllByLeelsComment(leelsCommentUtil.getComment(leelsCommentId));
    }
}
