package com.minsta.m.domain.feed.service.impl;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feed.FeedLike;
import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedLikeRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.FeedLikeCancelServicempl;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FeedLikeCancelServiceImpl implements FeedLikeCancelServicempl {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    @Override
    public void execute(Long feedId) {
        boolean valid = feedLikeRepository.existsById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId));
        if (!valid) {
            throw new BasicException(ErrorCode.FEED_NOT_LIKE);
        }

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));

        FeedLike feedLike = FeedLike.builder()
                .user(userUtil.getUser())
                .feed(feed)
                .feedLikeEmbedded(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId))
                .build();
        feedLikeRepository.save(feedLike);
    }
}