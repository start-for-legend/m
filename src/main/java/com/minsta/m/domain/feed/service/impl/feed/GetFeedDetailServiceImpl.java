package com.minsta.m.domain.feed.service.impl.feed;

import com.minsta.m.domain.feed.controller.data.response.FeedResponse;
import com.minsta.m.domain.feed.entity.feed.Feed;
import com.minsta.m.domain.feed.repository.FeedRepository;
import com.minsta.m.domain.feed.service.feed.GetFeedDetailService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.minsta.m.domain.feed.entity.feed.QFeedLike.feedLike;
import static com.minsta.m.domain.leels.entity.QLeelsLike.leelsLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetFeedDetailServiceImpl implements GetFeedDetailService {

    private final FeedRepository feedRepository;
    private final JPAQueryFactory em;

    @Override
    public FeedResponse execute(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new BasicException(ErrorCode.FEED_NOT_FOUND));

        return FeedResponse.builder()
                .feedId(feed.getFeedId())
                .userResponse(UserResponse.of(feed.getUser().getUserId(), feed.getUser().getNickName(), feed.getUser().getProfileUrl(), feed.getUser().getName()))
                .content(feed.getContent())
                .hashtags(feed.getHashtags())
                .fileUrls(feed.getFileUrls())
                .heartCount(getHeartCount(feedId))
                .build();
    }

    private int getHeartCount(Long id) {
        return em.selectFrom(feedLike)
                .where(feedLike.feedLikeEmbedded.feedId.eq(id))
                .fetch().size();
    }
}
