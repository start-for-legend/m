package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.service.leels.LeelsDetailService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.util.LeelsUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.minsta.m.domain.leels.entity.QLeelsLike.leelsLike;

@ReadOnlyService
@RequiredArgsConstructor
public class LeelsDetailServiceImpl implements LeelsDetailService {

    private final LeelsUtil leelsUtil;
    private final JPAQueryFactory em;

    @Override
    public LeelsResponse execute(Long leelsId) {

        Leels leels = leelsUtil.getLeels(leelsId);
        User current = leels.getUser();

        return LeelsResponse.builder()
                .leelsId(leels.getLeelsId())
                .author(UserResponse.of(current.getUserId(), current.getNickName(), current.getProfileUrl(), current.getName()))
                .content(leels.getContent())
                .hashtags(leels.getHashtags())
                .leelsUrl(leels.getLeelsUrl())
                .heartCount(getHeartCount(leels.getLeelsId()))
                .build();
    }

    private int getHeartCount(Long... id) {
        return em.selectFrom(leelsLike)
                .where(leelsLike.leels.leelsId.eq(id[0]))
                .fetch().size();
    }
}
