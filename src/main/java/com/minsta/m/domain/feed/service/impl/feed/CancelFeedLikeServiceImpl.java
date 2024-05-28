package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feed.FeedLike;
import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedLikeRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feed.CancelFeedLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelFeedLikeServiceImpl implements CancelFeedLikeService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    @Override
    public void execute(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));

        if (!feedLikeRepository.existsById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feed.getFeedId()))) {
            throw new BasicException(ErrorCode.FEED_NOT_LIKE);
        }

        FeedLike feedLike = feedLikeRepository.findById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId))
                .orElseThrow(() -> new BasicException(ErrorCode.SERVER_ERROR));
        feedLikeRepository.delete(feedLike);
    }
}
