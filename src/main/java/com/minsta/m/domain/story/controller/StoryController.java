package com.minsta.m.domain.story.controller;

import com.minsta.m.domain.story.controller.data.request.CreateStoryRequest;
import com.minsta.m.domain.story.service.CreateStoryService;
import com.minsta.m.domain.story.service.StoryReadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {

    private final CreateStoryService createStoryService;
    private final StoryReadService storyReadService;

    @PostMapping
    public ResponseEntity<HttpStatus> createStory(@RequestBody @Valid CreateStoryRequest createStoryRequest) {
        createStoryService.execute(createStoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity<HttpStatus> readStory(@PathVariable Long storyId) {
        storyReadService.execute(storyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
