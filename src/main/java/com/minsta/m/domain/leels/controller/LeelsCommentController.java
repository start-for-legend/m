package com.minsta.m.domain.leels.controller;

import com.minsta.m.domain.leels.controller.data.request.CreateLeelsCommentRequest;
import com.minsta.m.domain.leels.controller.data.response.LeelsCommentResponse;
import com.minsta.m.domain.leels.service.leelscomment.*;
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


@Tag(name = "http://10.53.68.120:80/leels-comment/{leelsId} 하위 API", description = "leels comment 관련 API")
@RestController
@RequestMapping("/leels-comment/{leelsId}")
@RequiredArgsConstructor
public class LeelsCommentController {

    private final CreateLeelsCommentService createLeelsCommentService;
    private final LeelsCommentDeleteService leelsCommentDeleteService;
    private final CreateLeelsCommentLikeService createLeelsCommentLikeService;
    private final UpdateLeelsCommentService updateLeelsCommentService;
    private final LeelsCommentLikeCancelService leelsCommentLikeCancelService;
    private final GetCommentListByLeelsIdService getCommentListByLeelsIdService;

    @Operation(summary = "Create Comment", description = "릴스에 대한 댓글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Create Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createLeelsComment(
            @RequestBody @Valid CreateLeelsCommentRequest createLeelsCommentRequest,
            @PathVariable Long leelsId
    ) {
        createLeelsCommentService.execute(createLeelsCommentRequest, leelsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Create like Comment", description = "릴스에 대한 좋아요 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Create Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> createLeelsCommentLike(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        createLeelsCommentLikeService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Comment", description = "릴스에 대한 댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not Your Comment"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> deleteLeelsComment(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        leelsCommentDeleteService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update Comment", description = "릴스에 대한 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not Your Comment"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PatchMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> updateComment(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId,
            @RequestBody @Valid CreateLeelsCommentRequest updateCommentRequest
    ) {
        updateLeelsCommentService.execute(leelsId, leelsCommentId, updateCommentRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "cancel like Comment", description = "댓글에 대한 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels or leelscomment not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PutMapping("/{leelsCommentId}")
    public ResponseEntity<HttpStatus> leelsCommentLikeCancel(
            @PathVariable Long leelsId,
            @PathVariable Long leelsCommentId
    ) {
        leelsCommentLikeCancelService.execute(leelsId, leelsCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "get comment list by leels id", description = "릴스 아이디로 댓글 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "get Comment",
                    headers = @Header(name = "accessToken",description = "accessToken Value", required = true),
                    content = @Content(schema = @Schema(implementation = LeelsCommentResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Invalid Token, Token Expired"),
            @ApiResponse(responseCode = "404", description = "leels not found"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping
    public ResponseEntity<List<LeelsCommentResponse>> getComments(
            @PathVariable Long leelsId,
            @RequestParam(name = "lastCommentId", defaultValue = "0") Long lastCommentId
    ) {
        var response = getCommentListByLeelsIdService.execute(leelsId, lastCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
