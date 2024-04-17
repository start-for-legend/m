package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;
import com.minsta.m.domain.leels.controller.data.response.LeelsReplyCommentResponse;
import com.minsta.m.domain.leels.service.leelscommentreply.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "http://10.53.68.120:80/leels-comment-reply/{leelsId}/{leelsCommentId} 하위 API", description = "leels comment reply 관련 API")
@RestController
@RequestMapping("/leels-comment-reply/{leelsId}/{leelsCommentId}")
@RequiredArgsConstructor
public class LeelsCommentReplyController {

    private final CreateLeelsCommentReplyService createLeelsCommentReplyService;
    private final DeleteLeelsCommentReplyService deleteLeelsCommentReplyService;
    private final UpdateLeelsCommentReplyService updateLeelsCommentReplyService;
    private final LeelsCommentReplyLikeService leelsCommentReplyLikeService;
    private final LeelsCommentReplyLikeCancelService leelsCommentReplyLikeCancelService;
    private final GetReplyCommentListByIdService getReplyCommentListByIdService;

    @Operation(summary = "Create Comment Reply", description = "댓글에 대한 답글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created Reply",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createCommentReply(
            @RequestBody @Valid CreateLeelsCommentRequest replyRequest,
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        createLeelsCommentReplyService.execute(leelsId, leelsCommentId, replyRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Comment Reply", description = "댓글에 대한 답글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete Reply",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not Your Reply"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> deleteCommentReply(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        deleteLeelsCommentReplyService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update Comment Reply", description = "댓글에 대한 답글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update Reply",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not Your Reply"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
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

    @Operation(summary = "Like Comment Reply", description = "댓글에 대한 답글 좋아요 누르기")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Create Like Reply",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/{leelsCommentReplyId}")
    public ResponseEntity<HttpStatus> likeCommentReply(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        leelsCommentReplyLikeService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Comment Reply Like", description = "댓글에 대한 답글 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete Reply Like",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not Your Like"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/{leelsCommentReplyId}/like")
    public ResponseEntity<HttpStatus> likeCommentReplyCancel(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @PathVariable Long leelsCommentReplyId
    ) {
        leelsCommentReplyLikeCancelService.execute(leelsId, leelsCommentId, leelsCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "get reply list by leels comment id", description = "댓글 아이디로 답글 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true),
                    content = @Content(schema = @Schema(implementation = LeelsReplyCommentResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping
    public ResponseEntity<List<LeelsReplyCommentResponse>> getReplyCommentList(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @RequestParam(name = "lastReplyCommentId", defaultValue = "0") Long lastReplyCommentId
    ) {
        var response = getReplyCommentListByIdService.execute(leelsCommentId, lastReplyCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
