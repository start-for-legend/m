package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.entity.feed.FeedLike;
import com.minsta.m.domain.feed.entity.feed.FeedLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedLikeRepository;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feed.FeedLikeService;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.CreateNotice;
import com.minsta.m.global.util.UserUtil;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class FeedLikeServiceImpl implements FeedLikeService {

    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final UserUtil userUtil;
    private final CreateNotice createNotice;

    @Override
    public void execute(Long feedId) {

        if (feedLikeRepository.existsById(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feedId))) {
            throw new BasicException(ErrorCode.FEED_EXIST_LIKE);
        }

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));
        FeedLike feedLike = FeedLike.builder()
                .user(userUtil.getUser())
                .feed(feed)
                .feedLikeEmbedded(new FeedLikeEmbedded(userUtil.getUser().getUserId(), feed.getFeedId()))
                .build();
        feedLikeRepository.save(feedLike);

        createNotice.createNotice(new NoticeRequest(
                NoticeType.FEED_LIKE,
                "feed/" + feedId,
                userUtil.getUser(),
                feed.getUser().getUserId()
        ));
    }
}
