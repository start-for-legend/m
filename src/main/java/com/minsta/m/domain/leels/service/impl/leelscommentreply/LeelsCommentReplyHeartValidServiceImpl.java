package com.minsta.m.domain.leels.service.impl.leelscommentreply;

import com.minsta.m.domain.leels.entity.LeelsCommentReplyLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyLikeRepository;
import com.minsta.m.domain.leels.service.leelscommentreply.LeelsCommentReplyHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class LeelsCommentReplyHeartValidServiceImpl implements LeelsCommentReplyHeartValidService {

    private final UserUtil userUtil;
    private final LeelsCommentReplyLikeRepository leelsCommentReplyLikeRepository;

    @Override
    public HeartValidResponse execute(Long leelsId, Long leelsCommentId, Long leelsCommentReplyId) {

        if (leelsCommentReplyLikeRepository.existsById(new LeelsCommentReplyLikeEmbedded(
                userUtil.getUser().getUserId(),
                leelsId,
                leelsCommentId,
                leelsCommentReplyId
        ))) return HeartValidResponse.of(true);
        else return HeartValidResponse.of(false);
    }
}
