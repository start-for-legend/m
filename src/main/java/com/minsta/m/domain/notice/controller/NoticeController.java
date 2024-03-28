package com.minsta.m.domain.notice.controller;

import com.minsta.m.domain.notice.controller.data.response.NoticeResponse;
import com.minsta.m.domain.notice.service.GetAllNoticeService;
import com.minsta.m.domain.notice.service.NoticeReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Tag(name = "http://10.53.68.120:80/notice 하위 API", description = "Notice 관련 API")
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final GetAllNoticeService getAllNoticeService;
    private final NoticeReadService noticeReadService;

    @Operation(summary = "get my notice", description = "자신의 알람 가져오기")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "text/event-stream",
                            array = @ArraySchema(schema = @Schema(implementation = NoticeResponse.class))
                    ),
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> getNotice(
            @RequestHeader(value = "last-notice-id", required = false, defaultValue = "") Long lastEventId
    ) {
        return new ResponseEntity<>(getAllNoticeService.execute(lastEventId), HttpStatus.OK);
    }

    @Operation(summary = "notice read without chat", description = "채팅을 제외한 알람 읽음 처리")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PatchMapping("/{lastNoticeId}")
    public ResponseEntity<HttpStatus> noticeRead(@PathVariable Long lastNoticeId) {
        noticeReadService.execute(lastNoticeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

