package com.minsta.m.domain.story.service.impl;

import com.minsta.m.domain.story.controller.data.request.CreateStoryRequest;
import com.minsta.m.domain.story.entity.Story;
import com.minsta.m.domain.story.repository.StoryRepository;
import com.minsta.m.domain.story.service.CreateStoryService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateStoryServiceImpl implements CreateStoryService {

    private final UserUtil userUtil;
    private final StoryRepository storyRepository;

    @Override
    public void execute(CreateStoryRequest createStoryRequest) {

        Story story = Story.builder()
                .user(userUtil.getUser())
                .url(createStoryRequest.getUrl())
                .isValid(true)
                .build();

        storyRepository.save(story);
    }
}
