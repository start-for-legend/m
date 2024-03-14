package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsLikeRepository;
import com.minsta.m.domain.leels.service.CancelLeelsLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelLeelsLikeServiceImpl implements CancelLeelsLikeService {

    private final UserUtil userUtil;
    private final LeelsLikeRepository likeRepository;

    @Override
    public void execute(Long leelsId) {

        LeelsLike leelsLike = likeRepository.findByLikeEmbedded(new LeelsLikeEmbedded(
                userUtil.getUser().getUserId(),
                leelsId
        ));
        
        likeRepository.delete(leelsLike);
    }
}
