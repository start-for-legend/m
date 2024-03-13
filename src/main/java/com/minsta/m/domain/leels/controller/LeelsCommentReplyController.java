package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leels/{leelsId}/{leelsCommentId}")
@RequiredArgsConstructor
public class LeelsCommentReplyController {

    private final CreateLeelsCommentReplyService createLeelsCommentReplyService;
    private final DeleteLeelsCommentReplyService deleteLeelsCommentReplyService;
    private final UpdateLeelsCommentReplyService updateLeelsCommentReplyService;
    private final LeelsCommentReplyLikeService leelsCommentReplyLikeService;
    private final LeelsCommentReplyLikeCancelService leelsCommentReplyLikeCancelService;

    @PostMapping("/reply")
    public ResponseEntity<HttpStatus> createCommentReply(
            @RequestBody @Valid CreateLeelsCommentRequest replyRequest,
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        createLeelsCommentReplyService.execute(leelsId, leelsCommentId, replyRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> deleteCommentReply(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        deleteLeelsCommentReplyService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> updateCommentReply(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId,
            @RequestBody @Valid CreateLeelsCommentRequest updateRequest
    ) {
        updateLeelsCommentReplyService.execute(leelsId, leelsCommentId, leelsCommentReplyId, updateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> likeCommentReply(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        leelsCommentReplyLikeService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> likeCommentReplyCancel(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        leelsCommentReplyLikeCancelService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
