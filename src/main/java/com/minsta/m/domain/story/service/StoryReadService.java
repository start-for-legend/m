package com.minsta.m.domain.story.service;

import com.minsta.m.domain.story.controller.data.response.StoryResponse;

public interface StoryReadService {

    StoryResponse execute(Long storyId);
}
