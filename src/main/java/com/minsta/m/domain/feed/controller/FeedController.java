package com.minsta.m.domain.feed.controller;

import com.minsta.m.domain.feed.controller.data.request.CreateFeedRequest;
import com.minsta.m.domain.feed.controller.data.response.FeedResponse;
import com.minsta.m.domain.feed.service.*;
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

@Tag(name = "http://10.53.68.120:80/feed 하위 API", description = "Feed 관련 API")
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final CreateFeedService createFeedService;
    private final GetFeedDetailService getFeedDetailService;
    private final FeedLikeService feedLikeService;
    private final FeedLikeCancelService feedLikeCancelServicempl;
    private final DeleteFeedService deleteFeedService;

    @Operation(summary = "create feed", description = "피드 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 생성",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateFeedRequest createFeedRequest) {
        createFeedService.execute(createFeedRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "get feed", description = "피드 디테일")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 가져오기",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = FeedResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedResponse> getFeed(@PathVariable Long feedId) {
        var res = getFeedDetailService.execute(feedId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "like feed", description = "피드 좋아요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "피드 좋아요",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Exist Like"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/{feedId}")
    public ResponseEntity<HttpStatus> feedPostLike(@PathVariable Long feedId) {
        feedLikeService.execute(feedId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "cancel like feed", description = "피드 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 좋아요 취소",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "not exist like"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PatchMapping("/{feedId}")
    public ResponseEntity<HttpStatus> feedCancelLike(@PathVariable Long feedId) {
        feedLikeCancelServicempl.execute(feedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "delete feed", description = "피드 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "피드 삭제",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "Not Owner Feed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{feedId}")
    public ResponseEntity<HttpStatus> deleteFeed(@PathVariable Long feedId) {
        deleteFeedService.execute(feedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
