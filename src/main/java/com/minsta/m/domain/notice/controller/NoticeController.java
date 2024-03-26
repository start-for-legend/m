package com.minsta.m.domain.notice.controller;

import com.minsta.m.domain.notice.service.GetAllNoticeService;
import com.minsta.m.domain.notice.service.NoticeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final GetAllNoticeService getAllNoticeService;
    private final NoticeReadService noticeReadService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getNotice(
            @RequestHeader(value = "last-notice-id", required = false, defaultValue = "") Long lastEventId
    ) {
        return getAllNoticeService.execute(lastEventId);
    }

    @PatchMapping("/{lastNoticeId}")
    public ResponseEntity<HttpStatus> noticeRead(@PathVariable Long lastNoticeId) {
        noticeReadService.execute(lastNoticeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

