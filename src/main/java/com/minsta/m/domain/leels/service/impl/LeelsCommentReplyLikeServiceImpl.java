package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsCommentReplyLike;
import com.minsta.m.domain.leels.entity.LeelsCommentReplyLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyLikeRepository;
import com.minsta.m.domain.leels.service.LeelsCommentReplyLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsCommentReplyUtil;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsCommentReplyLikeServiceImpl implements LeelsCommentReplyLikeService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentReplyUtil leelsCommentReplyUtil;
    private final LeelsCommentReplyLikeRepository leelsCommentReplyLikeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId) {

        LeelsCommentReplyLikeEmbedded likeEmbedded = new LeelsCommentReplyLikeEmbedded(
                userUtil.getUser().getUserId(),
                leelsId,
                leelsCommentId,
                leelsCommentReplyId
        );

        LeelsCommentReplyLike leelsCommentReplyLike = LeelsCommentReplyLike.builder()
                .leelsCommentReplyLikeEmbedded(likeEmbedded)
                .leels(leelsUtil.getLeels(leelsId))
                .leelsComment(leelsCommentUtil.getComment(leelsCommentId))
                .replyUser(leelsCommentReplyUtil.getReply(leelsCommentReplyId).getReplyUser())
                .user(userUtil.getUser())
                .build();

        leelsCommentReplyLikeRepository.save(leelsCommentReplyLike);
    }
}
