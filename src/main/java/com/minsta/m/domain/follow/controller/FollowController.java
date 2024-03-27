package com.minsta.m.domain.follow.controller;

import com.minsta.m.domain.follow.service.FollowCancelService;
import com.minsta.m.domain.follow.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "http://10.53.68.120:80/follow 하위 API", description = "Follow 관련 API")
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowCancelService followCancelService;

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
}
