package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.service.CreateLeelsCommentLikeService;
import com.minsta.m.domain.leels.service.CreateLeelsCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leels/{leelsId}")
@RequiredArgsConstructor
public class LeelsCommentController {

    private final CreateLeelsCommentService createLeelsCommentService;
    private final CreateLeelsCommentLikeService createLeelsCommentLikeService;

    @PostMapping
    public ResponseEntity<HttpStatus> createLeelsComment(
            @RequestBody @Valid CreateLeelsCommentRequest createLeelsCommentRequest,
            @PathVariable Long leelsId
    ) {
        createLeelsCommentService.execute(createLeelsCommentRequest, leelsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> createLeelsCommentLike(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        createLeelsCommentLikeService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
