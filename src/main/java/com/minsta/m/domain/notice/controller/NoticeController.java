package com.minsta.m.domain.notice.controller;

import com.minsta.m.domain.notice.service.GetAllNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;



@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final GetAllNoticeService getAllNoticeService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getNotice(
            @RequestHeader(value = "last-notice-id", required = false, defaultValue = "") Long lastEventId
    ) {
        return getAllNoticeService.execute(lastEventId);
    }
}

