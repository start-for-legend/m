package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsCommentEmbedded;
import com.minsta.m.domain.leels.entity.LeelsCommentLike;
import com.minsta.m.domain.leels.repository.LeelsCommentLikeRepository;
import com.minsta.m.domain.leels.service.CreateLeelsCommentLikeService;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.CreateNotice;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateLeelsCommentLikeServiceImpl implements CreateLeelsCommentLikeService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final CreateNotice createNotice;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentLikeRepository leelsCommentLikeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId) {

        LeelsCommentEmbedded embeddedId = new LeelsCommentEmbedded(
                userUtil.getUser().getUserId(),
                leelsUtil.getLeels(leelsId).getLeelsId(),
                leelsCommentUtil.getComment(leelsCommentId).getLeelsCommentId()
        );

        LeelsCommentLike leelsCommentLike = LeelsCommentLike.builder()
                .leelsCommentEmbedded(embeddedId)
                .user(userUtil.getUser())
                .leels(leelsUtil.getLeels(leelsId))
                .leelsComment(leelsCommentUtil.getComment(leelsCommentId))
                .build();

        createNotice.createNotice(new NoticeRequest(
                NoticeType.LIKE,
                (leelsId.toString() + leelsCommentId.toString()),
                userUtil.getUser(),
                leelsCommentUtil.getComment(leelsCommentId).getUser().getUserId()
        ));
        leelsCommentLikeRepository.save(leelsCommentLike);
    }
}
