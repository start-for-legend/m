package com.minsta.m.domain.feed.service.impl.feedcommentreply;

import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReplyLikeEmbedded;
import com.minsta.m.domain.feed.repository.FeedCommentReplyLikeRepository;
import com.minsta.m.domain.feed.service.feedcommentreply.FeedCommentReplyHeartValidService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.entity.HeartValidResponse;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@ReadOnlyService
@RequiredArgsConstructor
public class FeedCommentReplyHeartValidServiceImpl implements FeedCommentReplyHeartValidService {

    private final UserUtil userUtil;
    private final FeedCommentReplyLikeRepository feedCommentReplyLikeRepository;

    @Override
    public HeartValidResponse execute(Long feedId, Long feedCommentId, Long feedCommentReplyId) {

        if (feedCommentReplyLikeRepository.existsById(new FeedCommentReplyLikeEmbedded(
                userUtil.getUser().getUserId(),
                feedId,
                feedCommentId
                ,feedCommentReplyId
        ))) return HeartValidResponse.of(true);
        else return HeartValidResponse.of(false);
    }
}
