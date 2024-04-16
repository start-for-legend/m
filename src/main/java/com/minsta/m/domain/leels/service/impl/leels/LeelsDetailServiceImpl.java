package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsReplyCommentResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyRepository;
import com.minsta.m.domain.leels.repository.LeelsCommentRepository;
import com.minsta.m.domain.leels.service.impl.leels.GetReelsRecommendedServiceImpl;
import com.minsta.m.domain.leels.service.leels.LeelsDetailService;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.minsta.m.domain.leels.entity.QLeelsCommentLike.leelsCommentLike;
import static com.minsta.m.domain.leels.entity.QLeelsCommentReplyLike.leelsCommentReplyLike;
import static com.minsta.m.domain.leels.entity.QLeelsLike.leelsLike;

@ReadOnlyService
@RequiredArgsConstructor
public class LeelsDetailServiceImpl implements LeelsDetailService {

    private final LeelsUtil leelsUtil;
    private final JPAQueryFactory em;
    private final LeelsCommentRepository leelsCommentRepository;
    private final LeelsCommentReplyRepository leelsCommentReplyRepository;
    private final LeelsCommentUtil leelsCommentUtil;

    @Override
    public LeelsResponse execute(Long leelsId) {

        Leels leels = leelsUtil.getLeels(leelsId);
        User current = leels.getUser();

        LeelsResponse leelsResponse = LeelsResponse.builder()
                .leelsId(leels.getLeelsId())
                .author(UserResponse.of(current.getUserId(), current.getNickName(), current.getProfileUrl(), current.getName()))
                .content(leels.getContent())
                .hashtags(leels.getHashtags())
                .leelsUrl(leels.getLeelsUrl())
                .heartCount(getHeartCount(GetReelsRecommendedServiceImpl.Type.LEELS , leels.getLeelsId()))
                .build();

        return leelsResponse;
    }

    private int getHeartCount(GetReelsRecommendedServiceImpl.Type type, Long... id) {
        switch (type) {
            case LEELS -> {
                return em.selectFrom(leelsLike)
                        .where(leelsLike.leels.leelsId.eq(id[0]))
                        .fetch().size();
            }

            case LEELSCOMMENT -> {
                return em.selectFrom(leelsCommentLike)
                        .where(leelsCommentLike.leelsComment.leelsCommentId.eq(id[1]))
                        .fetch().size();

            }

            case LEELSCOMMENTREPLY -> {
                return em.selectFrom(leelsCommentReplyLike)
                        .where(leelsCommentReplyLike.leelsCommentReplyLikeEmbedded.leelsId.eq(id[0]))
                        .where(leelsCommentReplyLike.leelsCommentReplyLikeEmbedded.leelsCommentId.eq(id[1]))
                        .where(leelsCommentReplyLike.leelsCommentReplyLikeEmbedded.replyUserId.eq(id[2]))
                        .fetch().size();
            }

            default -> throw new BasicException(ErrorCode.SERVER_ERROR);
        }
    }

    private List<LeelsCommentResponse> getLeelsComment(Long leelsId) {
        List<LeelsCommentResponse> leelsCommentResponses = new ArrayList<>();
        List<LeelsComment> leelsComments = leelsCommentRepository.findAllByLeels(leelsUtil.getLeels(leelsId));

        for (LeelsComment comment : leelsComments) {
            boolean check;
            check = comment.getUpdatedAt() != null && comment.getUpdatedAt().isAfter(comment.getCreatedAt());
            User current = comment.getUser();

            LeelsCommentResponse leelsCommentResponse = LeelsCommentResponse.builder()
                    .leelsCommentId(comment.getLeelsCommentId())
                    .author(UserResponse.of(current.getUserId(), current.getNickName(), current.getProfileUrl(), current.getName()))
                    .comment(comment.getComment())
                    .heartCount(getHeartCount(GetReelsRecommendedServiceImpl.Type.LEELSCOMMENT, 0L, comment.getLeelsCommentId()))
                    .modify(check)
                    .leelsReplyCommentResponses(getLeelsCommentReply(leelsId, comment.getLeelsCommentId()))
                    .build();

            leelsCommentResponses.add(leelsCommentResponse);
        }

        return leelsCommentResponses;
    }

    private List<LeelsReplyCommentResponse> getLeelsCommentReply(Long leelsId, Long leelsCommentId) {
        List<LeelsReplyCommentResponse> leelsCommentResponses = new ArrayList<>();

        for (LeelsCommentReply leelsCommentReply :
                leelsCommentReplyRepository.findAllByLeelsAndLeelsComment(leelsUtil.getLeels(leelsId), leelsCommentUtil.getComment(leelsCommentId))) {

            boolean check;
            check = leelsCommentReply.getUpdatedAt() != null && leelsCommentReply.getUpdatedAt().isAfter(leelsCommentReply.getCreatedAt());

            User current = leelsCommentReply.getUser();

            LeelsReplyCommentResponse leelsReplyCommentResponse = LeelsReplyCommentResponse.builder()
                    .leelsReplyCommentId(leelsCommentReply.getLeelsCommentReplyId())
                    .author(UserResponse.of(current.getUserId(), current.getNickName(), current.getProfileUrl(), current.getName()))
                    .comment(leelsCommentReply.getComment())
                    .heartCount(getHeartCount(GetReelsRecommendedServiceImpl.Type.LEELSCOMMENTREPLY, leelsId, leelsCommentId, leelsCommentReply.getLeelsCommentReplyId()))
                    .modify(check)
                    .build();

            leelsCommentResponses.add(leelsReplyCommentResponse);
        }

        return leelsCommentResponses;
    }
}
