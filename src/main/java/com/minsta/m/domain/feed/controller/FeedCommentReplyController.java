package com.minsta.m.domain.feed.controller;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentReplyRequest;
import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentReplyRequest;
import com.minsta.m.domain.feed.controller.data.response.FeedReplyCommentResponse;
import com.minsta.m.domain.feed.entity.feedcommentreply.FeedCommentReply;
import com.minsta.m.domain.feed.service.feedcommentreply.*;
import com.minsta.m.global.entity.HeartValidResponse;
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


@Tag(name = "http://10.53.68.120:80/feed-comment-reply/{feedId}/{feedCommentId} 하위 API", description = "Feed Reply Comment 관련 API")
@RestController
@RequestMapping("/feed-comment-reply/{feedId}/{feedCommentId}")
@RequiredArgsConstructor
public class FeedCommentReplyController {

    private final CreateFeedCommentReplyService createFeedCommentReplyService;
    private final EditFeedCommentReplyService editFeedCommentReplyService;
    private final DeleteFeedCommentReplyService deleteFeedCommentReplyService;
    private final LikeFeedCommentReplyService likeFeedCommentReplyService;
    private final CancelLikeFeedCommentReplyService cancelLikeFeedCommentReplyService;
    private final GetReplyCommentsByIdService getReplyCommentsByIdService;
    private final FeedCommentReplyHeartValidService feedCommentReplyHeartValidService;

    @Operation(summary = "create feed reply comment", description = "피드 답글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 답글 생성",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createReply(
            @PathVariable Long feedCommentId,
            @PathVariable Long feedId,
            @RequestBody @Valid CreateFeedCommentReplyRequest createFeedCommentReplyRequest
    ) {
        createFeedCommentReplyService.execute(feedId, feedCommentId, createFeedCommentReplyRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "edit feed reply comment", description = "피드 답글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 답글 수정",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Owner of Reply"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PatchMapping("/{feedCommentReplyId}")
    public ResponseEntity<HttpStatus> editReply(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @PathVariable Long feedCommentReplyId,
            @RequestBody @Valid EditFeedCommentReplyRequest editFeedCommentReplyRequest
    ) {
        editFeedCommentReplyService.execute(feedId, feedCommentId, feedCommentReplyId, editFeedCommentReplyRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "delete feed reply comment", description = "피드 답글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 답글 삭제",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Owner of Reply"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{feedCommentReplyId}")
    public ResponseEntity<HttpStatus> editReply(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @PathVariable Long feedCommentReplyId
    ) {
        deleteFeedCommentReplyService.execute(feedId, feedCommentId, feedCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "post like feed reply comment", description = "피드 답글 좋아요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 답글 좋아요",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Already Like that reply"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/{feedCommentReplyId}")
    public ResponseEntity<HttpStatus> postLikeReply(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @PathVariable Long feedCommentReplyId
    ) {
        likeFeedCommentReplyService.execute(feedId, feedCommentId, feedCommentReplyId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "cancel like feed reply comment", description = "피드 답글 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 답글 좋아요 취소",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Like that reply"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{feedCommentReplyId}/like")
    public ResponseEntity<HttpStatus> cancelLikeReply(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @PathVariable Long feedCommentReplyId
    ) {
        cancelLikeFeedCommentReplyService.execute(feedId, feedCommentId, feedCommentReplyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "get feed reply comments", description = "피드 답글 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 답글 가져오기 성공",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = FeedReplyCommentResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found or Feed Comment Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<List<FeedReplyCommentResponse>> getReplyComments(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @RequestParam(name = "lastReplyCommentId", defaultValue = "0") Long lastReplyCommentId
    ) {
        var response = getReplyCommentsByIdService.execute(feedId, feedCommentId, lastReplyCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get heart valid feed comment reply", description = "피드 댓글-답글 좋아요 여부 체크")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 댓글-답글 좋아요 여부 가져옴",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = HeartValidResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/valid/{feedCommentReplyId}")
    public ResponseEntity<HeartValidResponse> isValidFeedCommentReply(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @PathVariable Long feedCommentReplyId
    ) {
        var response = feedCommentReplyHeartValidService.execute(feedId, feedCommentId, feedCommentReplyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
