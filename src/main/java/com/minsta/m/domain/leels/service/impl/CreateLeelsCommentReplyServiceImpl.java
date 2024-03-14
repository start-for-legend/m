package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.entity.LeelsCommentReply;
import com.minsta.m.domain.leels.repository.LeelsCommentReplyRepository;
import com.minsta.m.domain.leels.service.CreateLeelsCommentReplyService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateLeelsCommentReplyServiceImpl implements CreateLeelsCommentReplyService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentReplyRepository commentReplyRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId, CreateLeelsCommentRequest replyRequest) {

        User commentUser = leelsCommentUtil.getComment(leelsCommentId).getUser();

        LeelsCommentReply leelsCommentReply = LeelsCommentReply.builder()
                .user(commentUser)
                .replyUser(userUtil.getUser())
                .leels(leelsUtil.getLeels(leelsId))
                .leelsComment(leelsCommentUtil.getComment(leelsCommentId))
                .comment(replyRequest.getComment())
                .build();

        commentReplyRepository.save(leelsCommentReply);
    }
}
