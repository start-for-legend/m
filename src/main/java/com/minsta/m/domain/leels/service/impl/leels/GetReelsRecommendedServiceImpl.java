package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import com.minsta.m.domain.leels.service.leels.GetReelsRecommendedService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.minsta.m.domain.leels.entity.QLeelsLike.leelsLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetReelsRecommendedServiceImpl implements GetReelsRecommendedService {

    private final LeelsRepository leelsRepository;

    private final JPAQueryFactory em;

    @Override
    public List<LeelsResponse> execute() {
        List<LeelsResponse> leelsResponses = new ArrayList<>(25);

            for (Leels leels : leelsRepository.findDistinctLeelsRandomly()) {
                User current = leels.getUser();

                LeelsResponse leelsResponse = LeelsResponse.builder()
                        .leelsId(leels.getLeelsId())
                        .author(UserResponse.of(current.getUserId(), current.getNickName(), current.getProfileUrl(), current.getName()))
                        .content(leels.getContent())
                        .hashtags(leels.getHashtags())
                        .leelsUrl(leels.getLeelsUrl())
                        .heartCount(getHeartCount(leels.getLeelsId()))
                        .build();

                leelsResponses.add(leelsResponse);
            }

        return leelsResponses;
    }

    private int getHeartCount(Long id) {
        return em.selectFrom(leelsLike)
                .where(leelsLike.leels.leelsId.eq(id))
                .fetch().size();
    }
}
