package com.minsta.m.domain.leels.service.impl;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsRequest;
import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import com.minsta.m.domain.leels.service.CreateLeelsService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateLeelsServiceImpl implements CreateLeelsService {

    private final LeelsRepository leelsRepository;
    private final UserUtil userUtil;

    @Override
    public void execute(CreateLeelsRequest request) {

        Leels leels = Leels.builder()
                .content(request.getContent())
                .hashtags(request.getHashtags())
                .leelsUrl(request.getUrl())
                .user(userUtil.getUser())
                .build();

        leelsRepository.save(leels);
    }
}
