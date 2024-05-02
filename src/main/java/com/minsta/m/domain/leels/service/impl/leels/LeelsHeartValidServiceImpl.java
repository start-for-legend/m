package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsLikeRepository;
import com.minsta.m.domain.leels.service.leels.LeelsHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class LeelsHeartValidServiceImpl implements LeelsHeartValidService {

    private final UserUtil userUtil;
    private final LeelsLikeRepository likeRepository;

    @Override
    public HeartValidResponse execute(Long leelsId) {

        if (likeRepository.existsById(new LeelsLikeEmbedded(userUtil.getUser().getUserId(), leelsId)))
            return HeartValidResponse.of(true);
        else return HeartValidResponse.of(false);
    }
}
