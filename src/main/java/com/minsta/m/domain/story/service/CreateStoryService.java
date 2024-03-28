package com.minsta.m.domain.story.service;

import com.minsta.m.domain.story.controller.data.request.CreateStoryRequest;

public interface CreateStoryService {

    void execute(CreateStoryRequest createStoryRequest);
}
