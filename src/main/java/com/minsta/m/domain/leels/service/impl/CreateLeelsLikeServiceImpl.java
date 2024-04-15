package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.entity.LeelsLikeEmbedded;
import com.minsta.m.domain.leels.repository.LeelsLikeRepository;
import com.minsta.m.domain.leels.service.CreateLeelsLikeService;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.CreateNotice;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateLeelsLikeServiceImpl implements CreateLeelsLikeService {

    private final LeelsLikeRepository likeRepository;
    private final CreateNotice createNotice;
    private final LeelsUtil leelsUtil;
    private final UserUtil userUtil;

    @Override
    public void execute(Long leelsId) {

        LeelsLikeEmbedded likeEmbedded = new LeelsLikeEmbedded(
                userUtil.getUser().getUserId(),
                leelsId
        );
        if (likeRepository.existsByLikeEmbedded(likeEmbedded)) {
            throw new BasicException(ErrorCode.DENIED_TOW_TOUCH);
        }

        LeelsLike leelsLike = LeelsLike.builder()
                .likeEmbedded(likeEmbedded)
                .user(userUtil.getUser())
                .leels(leelsUtil.getLeels(leelsId))
                .build();

        createNotice.createNotice(new NoticeRequest(
                NoticeType.LEELS_LIKE,
                "leels/" + (leelsId.toString()),
                userUtil.getUser(),
                leelsUtil.getLeels(leelsId).getLeelsId()
        ));
        likeRepository.save(leelsLike);
    }
}
