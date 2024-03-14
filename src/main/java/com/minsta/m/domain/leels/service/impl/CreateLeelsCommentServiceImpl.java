package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.repository.LeelsCommentRepository;
import com.minsta.m.domain.leels.service.CreateLeelsCommentService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateLeelsCommentServiceImpl implements CreateLeelsCommentService {

    private final LeelsCommentRepository leelsCommentRepository;
    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;

    @Override
    public void execute(CreateLeelsCommentRequest createLeelsCommentRequest, Long leelsId) {

        LeelsComment leelsComment = LeelsComment.builder()
                .leels(leelsUtil.getLeels(leelsId))
                .user(userUtil.getUser())
                .comment(createLeelsCommentRequest.getComment())
                .leelsCommentReplies(new ArrayList<>())
                .build();

        leelsCommentRepository.save(leelsComment);
    }
}
