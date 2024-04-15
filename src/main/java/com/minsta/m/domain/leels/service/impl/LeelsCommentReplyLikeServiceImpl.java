package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsCommentReplyLike;
import com.minsta.m.domain.leels.entity.LeelsCommentReplyLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyLikeRepository;
import com.minsta.m.domain.leels.service.LeelsCommentReplyLikeService;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.*;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsCommentReplyLikeServiceImpl implements LeelsCommentReplyLikeService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final CreateNotice createNotice;
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

        createNotice.createNotice(new NoticeRequest(
                NoticeType.LEELS_COMMENT_REPLY_LIKE,
                "leels/" + (leelsId.toString() + leelsCommentId.toString() + leelsCommentReplyId.toString()),
                userUtil.getUser(),
                leelsCommentReplyUtil.getReply(leelsCommentReplyId).getUser().getUserId()
        ));
        leelsCommentReplyLikeRepository.save(leelsCommentReplyLike);
    }
}
