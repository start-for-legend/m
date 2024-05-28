package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsLikeRepository;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import com.minsta.m.domain.leels.service.leels.CancelLeelsLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelLeelsLikeServiceImpl implements CancelLeelsLikeService {

    private final UserUtil userUtil;
    private final LeelsRepository leelsRepository;
    private final LeelsLikeRepository leelsLikeRepository;

    @Override
    public void execute(Long leelsId) {

        Leels leels = leelsRepository.findById(leelsId).orElseThrow(() -> new BasicException(ErrorCode.LEELS_NOT_FOUND));

        LeelsLike leelsLike = leelsLikeRepository.findById(new LeelsLikeEmbedded(
                userUtil.getUser().getUserId(),
                leels.getLeelsId()
        )).orElseThrow(() -> new BasicException(ErrorCode.LEELS_NOT_LIKE));
        
        leelsLikeRepository.delete(leelsLike);
    }
}
