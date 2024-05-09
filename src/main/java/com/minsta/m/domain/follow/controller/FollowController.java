package com.minsta.m.domain.follow.controller;

import com.minsta.m.domain.follow.controller.data.response.RecommendedFollowerResponse;
import com.minsta.m.domain.follow.service.*;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import com.minsta.m.global.entity.HeartValidResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "http://10.53.68.120:80/follow 하위 API", description = "Follow 관련 API")
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowCancelService followCancelService;
    private final FollowRecommendedService followRecommendedService;
    private final FollowValidService followValidService;
    private final GetFollowerListService getFollowerListService;
    private final GetFollowingListService getFollowingListService;

    @Operation(summary = "follow", description = "팔로우하기")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "팔로우 성공",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<HttpStatus> follow(@PathVariable Long userId) {
        followService.execute(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "follow cancel", description = "팔로우 취소 하기")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "팔로우 취소 성공",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> followCancel(@PathVariable Long userId) {
        followCancelService.execute(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "get recommend follow", description = "팔로우 추천 목록 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = RecommendedFollowerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "User not found exception"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<List<RecommendedFollowerResponse>> getFollow() {
        var response = followRecommendedService.execute();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get follow valid", description = "팔로우 했는지 안했는지 여부 판단")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "User not found exception"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/valid/{userId}")
    public ResponseEntity<HeartValidResponse> isValidFollow(@PathVariable Long userId) {
        var response = followValidService.execute(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get follower follow", description = "팔로워 목록 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "User not found exception"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowerList(
            @PathVariable Long userId,
            @RequestParam(name = "lastUserId", defaultValue = "0") Long lastUserId
    ) {
        var response = getFollowerListService.execute(userId, lastUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get following list", description = "팔로잉 목록 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true),
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "User not found exception"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowingList(
            @PathVariable Long userId,
            @RequestParam(name = "lastUserId", defaultValue = "0") Long lastUserId
    ) {
        var response = getFollowingListService.execute(userId, lastUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
