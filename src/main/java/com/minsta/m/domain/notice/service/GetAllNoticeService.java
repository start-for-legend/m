package com.minsta.m.domain.notice.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface GetAllNoticeService {

    SseEmitter execute(Long lastEventId);
}
