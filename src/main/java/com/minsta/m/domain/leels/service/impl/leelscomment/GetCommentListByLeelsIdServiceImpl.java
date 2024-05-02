package com.minsta.m.domain.leels.service.impl.leelscomment;

import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.service.leelscomment.GetCommentListByLeelsIdService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.minsta.m.domain.leels.entity.QLeelsComment.leelsComment;
import static com.minsta.m.domain.leels.entity.QLeelsCommentLike.leelsCommentLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetCommentListByLeelsIdServiceImpl implements GetCommentListByLeelsIdService {

    private final JPAQueryFactory em;

    @Override
    public List<LeelsCommentResponse> execute(Long leelsId, Long lastCommentId) {

        List<LeelsComment> comments = em
                .selectFrom(leelsComment)
                .where(leelsComment.leelsCommentId.gt(lastCommentId))
                .where(leelsComment.leels.leelsId.eq(leelsId))
                .orderBy(leelsComment.leelsCommentId.asc())
                .limit(5).stream().toList();

        return comments.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private LeelsCommentResponse convert(LeelsComment leelsComment) {

        boolean check = leelsComment.getUpdatedAt() != null && leelsComment.getUpdatedAt().isAfter(leelsComment.getCreatedAt());

        return LeelsCommentResponse.builder()
                .leelsCommentId(leelsComment.getLeelsCommentId())
                .author(getUser(leelsComment.getUser()))
                .comment(leelsComment.getComment())
                .heartCount(getHeartCount(leelsComment.getLeelsCommentId()))
                .modify(check)
                .build();
    }

    private int getHeartCount(Long id) {
        return em.selectFrom(leelsCommentLike)
                .where(leelsCommentLike.leelsComment.leelsCommentId.eq(id))
                .fetch().size();

    }

    private UserResponse getUser(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getNickName(), user.getProfileUrl());
    }
}
