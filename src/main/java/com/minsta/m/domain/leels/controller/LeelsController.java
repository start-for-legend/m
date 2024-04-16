package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsRequest;
import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;
import com.minsta.m.domain.leels.service.leels.*;
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


@Tag(name = "http://10.53.68.120:80/leels 하위 API", description = "Leels 관련 API")
@RestController
@RequestMapping("/leels")
@RequiredArgsConstructor
public class LeelsController {

    private final CreateLeelsService createLeelsService;
    private final LeelsDeleteService leelsDeleteService;
    private final CreateLeelsLikeService createLeelsLikeService;
    private final CancelLeelsLikeService cancelLeelsLikeService;
    private final GetReelsRecommendedService getReelsRecommnededService;
    private final LeelsDetailService leelsDetailService;


    @Operation(summary = "create leels", description = "릴스 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "릴스 생성",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CreateLeelsRequest createLeelsRequest) {
        createLeelsService.execute(createLeelsRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "delete leels", description = "릴스 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, 로그인 성공 및 토큰 발급",
                    headers = @Header(name = "accessToken", description = "accessToken Value", required = true)),
                    @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
                    @ApiResponse(responseCode = "403", description = "Owner만 삭제 가능"),
                    @ApiResponse(responseCode = "404", description = "Leels Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @DeleteMapping("/{leelsId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long leelsId) {
        leelsDeleteService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "post like leels", description = "릴스 좋아요 하기")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "릴스 좋아요 됨",
                    headers = @Header(name = "accessToken", description = "accessToken Value", required = true)
            ),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "403", description = "좋아요를 두번 요청함"),
            @ApiResponse(responseCode = "404", description = "leels or leelsComment or leelsCommentReply Not Found By Server error code"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping("/{leelsId}/like")
    public ResponseEntity<HttpStatus> like(@PathVariable Long leelsId) {
        createLeelsLikeService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "cancel leels like", description = "릴스 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "릴스 좋아요 취소",
                    headers = @Header(name = "accessToken", description = "accessToken Value", required = true)
            ),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PatchMapping("/{leelsId}/like")
    public ResponseEntity<HttpStatus> cancel(@PathVariable Long leelsId) {
        cancelLeelsLikeService.execute(leelsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "get leels", description = "릴스 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "릴스 가져옴",
                    headers = @Header(name = "accessToken", description = "accessToken Value", required = true),
                    content = @Content(schema = @Schema(implementation = LeelsResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "leels or leelsComment or leelsCommentReply Not Found By Server error code"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<List<LeelsResponse>> getLeels() {
        var response = getReelsRecommnededService.execute();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get leels by id", description = "릴스아이디로 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "릴스 가져옴",
                    headers = @Header(name = "accessToken", description = "accessToken Value", required = true),
                    content = @Content(schema = @Schema(implementation = LeelsResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "leels or leelsComment or leelsCommentReply Not Found By Server error code"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/{leelsId}")
    public ResponseEntity<LeelsResponse> getLeelsById(@PathVariable Long leelsId) {
        var response = leelsDetailService.execute(leelsId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
