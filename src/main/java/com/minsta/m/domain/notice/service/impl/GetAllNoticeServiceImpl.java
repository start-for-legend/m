package com.minsta.m.domain.notice.service.impl;

import com.minsta.m.domain.notice.controller.data.response.NoticeResponse;
import com.minsta.m.domain.notice.entity.Notice;
import com.minsta.m.domain.notice.repository.NoticeRepository;
import com.minsta.m.domain.notice.service.GetAllNoticeService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@ReadOnlyService
@RequiredArgsConstructor
public class GetAllNoticeServiceImpl implements GetAllNoticeService {

    private final UserUtil userUtil;
    private final SseEmitter sseEmitter;
    private final NoticeRepository noticeRepository;

    @Override
    public SseEmitter execute(Long lastEventId) {

        for (Notice notice : noticeRepository.findAllByUserAndNoticeIdAfter(userUtil.getUser(), lastEventId)) {
            if (!notice.isValid()) {
                continue;
            }

            NoticeResponse convert = convertResponse(notice);
            sendNotification(sseEmitter, String.valueOf(notice.getNoticeId()), convert);
        }

        return sseEmitter;
    }

    private void sendNotification(SseEmitter emitter, String eventId, Object data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            emitter.completeWithError(exception);
        }
    }

    private NoticeResponse convertResponse(Notice notice) {
        switch (notice.getNoticeType()) {
            case MESSAGE -> {
                return NoticeResponse.builder()
                        .noticeId(notice.getNoticeId())
                        .noticeType(notice.getNoticeType())
                        .url(null)
                        .isRead(notice.isRead())
                        .createAt(notice.getCreateAt())
                        .build();
            }

            case COMMENT, LIKE -> {
                return NoticeResponse.builder()
                        .noticeId(notice.getNoticeId())
                        .noticeType(notice.getNoticeType())
                        .userNickName(notice.getUser().getNickName())
                        .url(notice.getUrl())
                        .isRead(notice.isRead())
                        .createAt(notice.getCreateAt())
                        .build();
            }

            default -> throw new BasicException(ErrorCode.BAD_REQUEST);
        }
    }
}
