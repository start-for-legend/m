package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsCommentLike;
import com.minsta.m.domain.leels.repository.LeelsCommentLikeRepository;
import com.minsta.m.domain.leels.service.LeelsCommentLikeCancelService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.minsta.m.domain.leels.entity.QLeelsCommentLike.leelsCommentLike;

@Slf4j
@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsCommentLikeCancelServiceImpl implements LeelsCommentLikeCancelService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentLikeRepository leelsCommentLikeRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void execute(Long leelsId, Long leelsCommentId) {
        LeelsCommentLike like = jpaQueryFactory
                .selectFrom(leelsCommentLike)
                .where(leelsCommentLike.user.eq(userUtil.getUser()))
                .where(leelsCommentLike.leels.eq(leelsUtil.getLeels(leelsId)))
                .where(leelsCommentLike.leelsComment.eq(leelsCommentUtil.getComment(leelsCommentId)))
                .fetchOne();

        leelsCommentLikeRepository.delete(Objects.requireNonNull(like));
    }
}
