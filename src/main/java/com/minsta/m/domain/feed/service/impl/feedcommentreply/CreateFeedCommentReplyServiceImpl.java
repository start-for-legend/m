package com.minsta.m.domain.feed.service.impl.feedcommentreply;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentReplyRequest;
import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReply;
import com.minsta.m.domain.feed.repository.FeedCommentReplyRepository;
import com.minsta.m.domain.feed.repository.FeedCommentRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feedcommentreply.CreateFeedCommentReplyService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateFeedCommentReplyServiceImpl implements CreateFeedCommentReplyService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentReplyRepository feedCommentReplyRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId, CreateFeedCommentReplyRequest createFeedCommentReplyRequest) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));
        FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow(() -> new BasicException(ErrorCode.FEED_COMMENT_NOT_FOUND));

        FeedCommentReply feedCommentReply = FeedCommentReply.builder()
                .user(userUtil.getUser())
                .feed(feed)
                .feedComment(feedComment)
                .content(createFeedCommentReplyRequest.getContent())
                .build();

        feedCommentReplyRepository.save(feedCommentReply);
    }
}
