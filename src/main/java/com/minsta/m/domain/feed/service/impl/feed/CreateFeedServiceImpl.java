package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedRequest;
import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feed.CreateFeedService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateFeedServiceImpl implements CreateFeedService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;

    @Override
    public void execute(CreateFeedRequest createFeedRequest) {

        Feed feed = Feed.builder()
                .user(userUtil.getUser())
                .content(createFeedRequest.getContent())
                .hashtags(createFeedRequest.getHashtags())
                .fileUrls(createFeedRequest.getUrl())
                .build();

        feedRepository.save(feed);
    }
}
