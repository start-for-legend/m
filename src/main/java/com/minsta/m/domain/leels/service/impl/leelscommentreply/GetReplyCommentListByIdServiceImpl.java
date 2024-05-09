package com.minsta.m.domain.leels.service.impl.leelscommentreply;

import com.minsta.m.domain.leels.controller.data.response.LeelsReplyCommentResponse;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.service.leelscommentreply.GetReplyCommentListByIdService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.minsta.m.domain.leels.entity.QLeelsCommentReply.leelsCommentReply;
import static com.minsta.m.domain.leels.entity.QLeelsCommentReplyLike.leelsCommentReplyLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetReplyCommentListByIdServiceImpl implements GetReplyCommentListByIdService {

    private final JPAQueryFactory em;

    @Override
    public List<LeelsReplyCommentResponse> execute(Long leelsCommentId, Long lastReplyCommentId) {

        List<LeelsCommentReply> leelsCommentReplies = em
                .selectFrom(leelsCommentReply)
                .where(leelsCommentReply.leelsComment.leelsCommentId.eq(lastReplyCommentId))
                .where(leelsCommentReply.leelsCommentReplyId.gt(lastReplyCommentId))
                .orderBy(leelsCommentReply.leelsCommentReplyId.asc())
                .limit(5).stream().toList();

        return leelsCommentReplies.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private LeelsReplyCommentResponse convert(LeelsCommentReply leelsCommentReply) {

        boolean check = leelsCommentReply.getUpdatedAt() != null && leelsCommentReply.getUpdatedAt().isAfter(leelsCommentReply.getCreatedAt());

        return LeelsReplyCommentResponse.builder()
                .leelsReplyCommentId(leelsCommentReply.getLeelsCommentReplyId())
                .author(getUser(leelsCommentReply.getUser()))
                .comment(leelsCommentReply.getComment())
                .heartCount(getHeartCount(leelsCommentReply.getLeelsCommentReplyId()))
                .modify(check)
                .build();
    }

    private int getHeartCount(Long id) {
        return em.
                selectFrom(leelsCommentReplyLike)
                .where(leelsCommentReplyLike.leelsCommentReplyLikeEmbedded.leelsCommentReplyId.eq(id))
                .fetch().size();
    }

    private UserResponse getUser(User user) {
        return UserResponse.of(user.getUserId(), user.getNickName(), user.getProfileUrl(), user.getName());
    }
}
