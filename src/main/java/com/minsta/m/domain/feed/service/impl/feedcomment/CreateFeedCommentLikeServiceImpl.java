package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLike;
import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentLikeRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feedcomment.CreateFeedCommentLikeService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateFeedCommentLikeServiceImpl implements CreateFeedCommentLikeService {

    private final UserUtil userUtil;
    private final FeedRepository feedRepository;
    private final FeedCommentLikeRepository feedCommentLikeRepository;

    @Override
    public void execute(Long feedId, Long feedCommentId) {

        if (feedCommentLikeRepository.existsById(new FeedCommentLikeEmbedded(
                userUtil.getUser().getUserId(),
                feedId,
                feedCommentId
        ))) {
            throw new BasicException(ErrorCode.FEED_EXIST_LIKE);
        }

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));

        FeedCommentLike feedCommentLike = FeedCommentLike.builder()
                .feedCommentLikeEmbedded(new FeedCommentLikeEmbedded(
                        userUtil.getUser().getUserId(),
                        feed.getFeedId(),
                        feedCommentId
                ))
                .feed(feed)
                .user(userUtil.getUser())
                .build();

        feedCommentLikeRepository.save(feedCommentLike);
    }
}
