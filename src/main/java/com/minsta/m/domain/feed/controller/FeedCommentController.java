package com.minsta.m.domain.feed.controller;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentRequest;
import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentRequest;
import com.minsta.m.domain.feed.service.feedcomment.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed/{feedId}")
@RequiredArgsConstructor
public class FeedCommentController {

    private final CreateFeedCommentService createFeedCommentService;
    private final CreateFeedCommentLikeService createFeedCommentLikeService;
    private final DeleteFeedCommentService deleteFeedCommentService;
    private final EditFeedCommentService editFeedCommentService;
    private final CommentCancelLikeService commentCancelLikeService;

    @PostMapping
    public ResponseEntity<HttpStatus> createFeedComment(
            @RequestBody @Valid CreateFeedCommentRequest createFeedCommentRequest,
            @PathVariable Long feedId
    ) {
        createFeedCommentService.execute(createFeedCommentRequest, feedId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> createFeedCommentLike(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        createFeedCommentLikeService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{feedCommentId}/like")
    public ResponseEntity<HttpStatus> cancelCommentLike(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        commentCancelLikeService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> deleteFeedComment(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        deleteFeedCommentService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> editComment(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @RequestBody @Valid EditFeedCommentRequest editFeedCommentRequest
    ) {
        editFeedCommentService.execute(feedId, feedCommentId, editFeedCommentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
