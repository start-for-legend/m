package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.service.*;
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
    private final LeelsCommentDeleteService leelsCommentDeleteService;
    private final CreateLeelsCommentLikeService createLeelsCommentLikeService;
    private final UpdateLeelsCommentService updateLeelsCommentService;
    private final LeelsCommentLikeCancelService leelsCommentLikeCancelService;

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

    @DeleteMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> deleteLeelsComment(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        leelsCommentDeleteService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> updateComment(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @RequestBody @Valid CreateLeelsCommentRequest updateCommentRequest
    ) {
        updateLeelsCommentService.execute(leelsId, leelsCommentId, updateCommentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> leelsCommentLikeCancel(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        leelsCommentLikeCancelService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
