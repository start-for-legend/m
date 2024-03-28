package com.minsta.m.domain.story.service.impl;

import com.minsta.m.domain.story.entity.Story;
import com.minsta.m.domain.story.repository.StoryRepository;
import com.minsta.m.domain.story.service.StoryReadService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class StoryReadServiceImpl implements StoryReadService {

    private final StoryRepository storyRepository;
    private final UserUtil userUtil;

    @Override
    public void execute(Long storyId) {

        Story story = storyRepository.findById(storyId).orElseThrow(() -> new BasicException(ErrorCode.STORY_NOT_FOUND));
        story.addWatchers(userUtil.getUser().getUserId());
        storyRepository.save(story);
    }
}
