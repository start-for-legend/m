package com.minsta.m.domain.leels.service.impl.leelscommentreply;

import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.entity.LeelsCommentReplyLike;
import com.minsta.m.domain.leels.entity.LeelsCommentReplyLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyLikeRepository;
import com.minsta.m.domain.leels.service.leelscommentreply.LeelsCommentReplyLikeCancelService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentReplyUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsCommentReplyLikeCancelServiceImpl implements LeelsCommentReplyLikeCancelService {

    private final UserUtil userUtil;
    private final LeelsCommentReplyUtil leelsCommentReplyUtil;
    private final LeelsCommentReplyLikeRepository likeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId) {
        LeelsCommentReply leelsCommentReply = leelsCommentReplyUtil.getReply(leelsCommentReplyId);
        if (!leelsCommentReply.getReplyUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.CANCEL_PERMISSION_DENIED);
        }

        LeelsCommentReplyLike leelsCommentReplyLike = likeRepository.findByLeelsCommentReplyLikeEmbedded(new LeelsCommentReplyLikeEmbedded(
                leelsCommentReply.getUser().getUserId(),
                userUtil.getUser().getUserId(),
                leelsId,
                leelsCommentId
        ));

        likeRepository.delete(leelsCommentReplyLike);
    }
}
