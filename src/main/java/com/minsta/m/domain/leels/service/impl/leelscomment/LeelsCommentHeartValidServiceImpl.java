package com.minsta.m.domain.leels.service.impl.leelscomment;

import com.minsta.m.domain.leels.entity.LeelsCommentEmbedded;
import com.minsta.m.domain.leels.repository.LeelsCommentLikeRepository;
import com.minsta.m.domain.leels.service.leelscomment.LeelsCommentHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class LeelsCommentHeartValidServiceImpl implements LeelsCommentHeartValidService {

    private final UserUtil userUtil;
    private final LeelsCommentLikeRepository leelsCommentLikeRepository;

    @Override
    public HeartValidResponse execute(Long leelsId, Long leelsCommentId) {

        if (leelsCommentLikeRepository.existsById(new LeelsCommentEmbedded(
                userUtil.getUser().getUserId(),
                leelsId,
                leelsCommentId
        ))) return HeartValidResponse.of(true);
        else return HeartValidResponse.of(false);
    }
}
