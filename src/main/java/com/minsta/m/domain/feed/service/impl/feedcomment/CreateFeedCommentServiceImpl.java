package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentRequest;
import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feedcomment.CreateFeedCommentService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateFeedCommentServiceImpl implements CreateFeedCommentService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;

    @Override
    public void execute(CreateFeedCommentRequest createFeedCommentRequest, Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));

        FeedComment feedComment = FeedComment.builder().
                content(createFeedCommentRequest.getContent())
                .user(userUtil.getUser())
                .feed(feed)
                .build();

        feedCommentRepository.save(feedComment);
    }
}
