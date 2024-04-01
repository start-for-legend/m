package com.minsta.m.domain.feed.service.impl;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.DeleteFeedService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class DeleteFeedServiceImpl implements DeleteFeedService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;

    @Override
    public void execute(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));
        if (!feed.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.NOT_OWNER_FEED);
        }

        feedRepository.delete(feed);
    }
}
