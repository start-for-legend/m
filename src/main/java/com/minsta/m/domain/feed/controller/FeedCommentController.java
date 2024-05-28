package com.minsta.m.domain.feed.controller;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedCommentRequest;
import com.minsta.m.domain.feed.controller.data.request.EditFeedCommentRequest;
import com.minsta.m.domain.feed.controller.data.response.FeedCommentResponse;
import com.minsta.m.domain.feed.service.feedcomment.*;
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


@Tag(name = "http://10.53.68.120:80/feed-comment/{feedId} 하위 API", description = "Feed Comment 관련 API")
@RestController
@RequestMapping("/feed-comment/{feedId}")
@RequiredArgsConstructor
public class FeedCommentController {

    private final CreateFeedCommentService createFeedCommentService;
    private final CreateFeedCommentLikeService createFeedCommentLikeService;
    private final DeleteFeedCommentService deleteFeedCommentService;
    private final EditFeedCommentService editFeedCommentService;
    private final CancelFeedCommentLikeService commentCancelLikeService;
    private final GetCommentsByIdService getCommentsByIdService;
    private final FeedCommentHeartValidService feedCommentHeartValidService;

    @Operation(summary = "create feed comment", description = "피드 댓글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 댓글 생성",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createFeedComment(
            @RequestBody @Valid CreateFeedCommentRequest createFeedCommentRequest,
            @PathVariable Long feedId
    ) {
        createFeedCommentService.execute(createFeedCommentRequest, feedId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "post like feed comment", description = "피드 댓글 좋아요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 댓글 좋아요",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Already Like Comment"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> createFeedCommentLike(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        createFeedCommentLikeService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "cancel like feed comment", description = "피드 댓글 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 댓글 좋아요 취소",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Like Comment"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{feedCommentId}/like")
    public ResponseEntity<HttpStatus> cancelCommentLike(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        commentCancelLikeService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "delete feed comment", description = "피드 댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 댓글 삭제",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Owner Comment"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> deleteFeedComment(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        deleteFeedCommentService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "edit feed comment", description = "피드 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 댓글 수정",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Owner Comment"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PatchMapping("/{feedCommentId}")
    public ResponseEntity<HttpStatus> editComment(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId,
            @RequestBody @Valid EditFeedCommentRequest editFeedCommentRequest
    ) {
        editFeedCommentService.execute(feedId, feedCommentId, editFeedCommentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "get feed comments", description = "피드 댓글 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 댓글 가져오기 성공",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = FeedCommentResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "Feed Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<List<FeedCommentResponse>> getCommentsById(
            @PathVariable Long feedId,
            @RequestParam(name = "lastCommentId", defaultValue = "0") Long lastCommentId
    ) {
        var response = getCommentsByIdService.execute(feedId, lastCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get heart valid feed comment", description = "피드 댓글 좋아요 여부 체크")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 댓글 좋아요 여부 가져옴",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = HeartValidResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/valid/{feedCommentId}")
    public ResponseEntity<HeartValidResponse> isValidFeedComment(
            @PathVariable Long feedId,
            @PathVariable Long feedCommentId
    ) {
        var response = feedCommentHeartValidService.execute(feedId, feedCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
