package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsReplyCommentResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.entity.LeelsLike;
import com.minsta.m.domain.leels.repository.*;
import com.minsta.m.domain.leels.service.GetReelsRecommnededService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.minsta.m.domain.leels.entity.QLeelsCommentLike.leelsCommentLike;
import static com.minsta.m.domain.leels.entity.QLeelsCommentReplyLike.leelsCommentReplyLike;
import static com.minsta.m.domain.leels.entity.QLeelsLike.leelsLike;

@ReadOnlyService
@RequiredArgsConstructor
public class GetReelsRecommendedServiceImpl implements GetReelsRecommnededService {

    enum Type {
        LEELS,
        LEELSCOMMENT,
        LEELSCOMMENTREPLY
    }

    private final UserUtil userUtil;

    private final LeelsUtil leelsUtil;
    private final LeelsRepository leelsRepository;
    private final LeelsLikeRepository leelsLikeRepository;

    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentRepository leelsCommentRepository;

    private final LeelsCommentReplyRepository leelsCommentReplyRepository;

    private final JPAQueryFactory em;

    @Override
    public List<LeelsResponse> execute() {
        List<LeelsResponse> leelsResponses = new ArrayList<>(25);

        if (leelsLikeRepository.countByUser(userUtil.getUser()) > 0) {
            List<LeelsLike> leelsLikes = em.selectFrom(leelsLike)
                    .where(leelsLike.user.eq(userUtil.getUser()))
                    .fetch();

            String hash = null;
            for (LeelsLike like : leelsLikes) {
                if (like.getLeels().getHashtags().get(1) != null) {
                    hash = like.getLeels().getHashtags().get(1);
                    break;
                }
            }

            for (Leels leels : leelsRepository.findAllByHashtagsContains(hash)) {
                LeelsResponse leelsResponse = LeelsResponse.builder()
                        .leelsId(leels.getLeelsId())
                        .author(leels.getUser())
                        .content(leels.getContent())
                        .hashtags(leels.getHashtags())
                        .leelsUrl(leels.getLeelsUrl())
                        .leelsCommentResponses(getLeelsComment(leels.getLeelsId()))
                        .heartCount(getHeartCount(Type.LEELS, leels.getLeelsId()))
                        .build();

                leelsResponses.add(Math.toIntExact(leels.getLeelsId()), leelsResponse);
            }

        } else {
            for (Leels leels : leelsRepository.findDistinctLeelsRandomly()) {

                LeelsResponse leelsResponse = LeelsResponse.builder()
                        .leelsId(leels.getLeelsId())
                        .author(leels.getUser())
                        .content(leels.getContent())
                        .hashtags(leels.getHashtags())
                        .leelsUrl(leels.getLeelsUrl())
                        .leelsCommentResponses(getLeelsComment(leels.getLeelsId()))
                        .heartCount(getHeartCount(Type.LEELS, leels.getLeelsId()))
                        .build();

                leelsResponses.add(leelsResponse);
            }

        }
        return leelsResponses;
    }

    private int getHeartCount(Type type, Long... id) {
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

            LeelsCommentResponse leelsCommentResponse = LeelsCommentResponse.builder()
                    .leelsCommentId(comment.getLeelsCommentId())
                    .author(comment.getUser())
                    .comment(comment.getComment())
                    .heartCount(getHeartCount(Type.LEELSCOMMENT, 0L, comment.getLeelsCommentId()))
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

            LeelsReplyCommentResponse leelsReplyCommentResponse = LeelsReplyCommentResponse.builder()
                    .leelsReplyCommentId(leelsCommentReply.getLeelsCommentReplyId())
                    .author(leelsCommentReply.getReplyUser())
                    .comment(leelsCommentReply.getComment())
                    .heartCount(getHeartCount(Type.LEELSCOMMENTREPLY, leelsId, leelsCommentId, leelsCommentReply.getLeelsCommentReplyId()))
                    .modify(check)
                    .build();

            leelsCommentResponses.add(leelsReplyCommentResponse);
        }

        return leelsCommentResponses;
    }
}
