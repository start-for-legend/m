package com.minsta.m.domain.feed.service.impl.feedcomment;

import com.minsta.m.domain.feed.entity.feedcomment.FeedCommentLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentLikeRepository;
import com.minsta.m.domain.feed.service.feedcomment.FeedCommentHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class FeedCommentHeartValidServiceImpl implements FeedCommentHeartValidService {

    private final UserUtil userUtil;
    private final FeedCommentLikeRepository feedCommentLikeRepository;

    @Override
    public HeartValidResponse execute(Long feedId, Long feedCommentId) {

        if (feedCommentLikeRepository.existsById(new FeedCommentLikeEmbedded(
                userUtil.getUser().getUserId(),
                feedId,
                feedCommentId
        ))) return HeartValidResponse.of(true);
        else return HeartValidResponse.of(false);
    }
}
