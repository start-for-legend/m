package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedLikeRepository;
import com.minsta.m.domain.feed.service.feed.FeedHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class FeedHeartValidServiceImpl implements FeedHeartValidService {

    private final UserUtil userUtil;
    private final FeedLikeRepository feedLikeRepository;

    @Override
    public HeartValidResponse execute(Long feedId) {

        if (feedLikeRepository.existsById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId))) {
            return HeartValidResponse.of(true);
        } return HeartValidResponse.of(false);
    }
}
