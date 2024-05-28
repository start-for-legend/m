package com.minsta.m.domain.leels.service.impl.leelscommentreply;

import com.minsta.m.domain.leels.entity.*;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyLikeRepository;
import com.minsta.m.domain.leels.service.leelscommentreply.CancelLeelsCommentReplyLikeService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentReplyUtil;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelLeelsCommentReplyLikeServiceImpl implements CancelLeelsCommentReplyLikeService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentReplyUtil leelsCommentReplyUtil;
    private final LeelsCommentReplyLikeRepository likeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId) {

        User currentUser = userUtil.getUser();
        Leels leels = leelsUtil.getLeels(leelsId);
        LeelsComment leelsComment = leelsCommentUtil.getComment(leelsCommentId);
        LeelsCommentReply leelsCommentReply = leelsCommentReplyUtil.getReply(leelsCommentReplyId);

        LeelsCommentReplyLike leelsCommentReplyLike = likeRepository.findById(new LeelsCommentReplyLikeEmbedded(
                leelsCommentReply.getUser().getUserId(),
                currentUser.getUserId(),
                leels.getLeelsId(),
                leelsComment.getLeelsCommentId()
        )).orElseThrow(() -> new BasicException(ErrorCode.LEELS_NOT_LIKE));

        likeRepository.delete(leelsCommentReplyLike);
    }
}
