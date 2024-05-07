package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.entity.feed.FeedLike;
import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedLikeRepository;
import com.minsta.m.domain.feed.service.feed.FeedLikeCancelService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FeedLikeCancelServiceImpl implements FeedLikeCancelService {

    private final UserUtil userUtil;
    private final FeedLikeRepository feedLikeRepository;

    @Override
    public void execute(Long feedId) {
        boolean valid = feedLikeRepository.existsById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId));
        if (!valid) {
            throw new BasicException(ErrorCode.FEED_NOT_LIKE);
        }

        FeedLike feedLike = feedLikeRepository.findById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId))
                .orElseThrow(() -> new BasicException(ErrorCode.SERVER_ERROR));
        feedLikeRepository.delete(feedLike);
    }
}
