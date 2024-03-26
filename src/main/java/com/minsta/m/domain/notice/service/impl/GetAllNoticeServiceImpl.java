package com.minsta.m.domain.notice.service.impl;

import com.minsta.m.domain.notice.controller.data.response.NoticeResponse;
import com.minsta.m.domain.notice.entity.Notice;
import com.minsta.m.domain.notice.repository.NoticeRepository;
import com.minsta.m.domain.notice.service.GetAllNoticeService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.minsta.m.domain.chat.entity.QChatHistory.chatHistory;

@ReadOnlyService
@RequiredArgsConstructor
public class GetAllNoticeServiceImpl implements GetAllNoticeService {

    private final UserUtil userUtil;
    private final SseEmitter sseEmitter;
    private final NoticeRepository noticeRepository;
    private final JPAQueryFactory em;

    @Override
    public SseEmitter execute(Long lastEventId) {

        for (Notice notice : noticeRepository.findAllByUserAndNoticeIdAfter(userUtil.getUser(), lastEventId)) {
            if (!notice.isValid()) {
                continue;
            }

            NoticeResponse convert = convertResponse(notice);
            sendNotification(sseEmitter, String.valueOf(notice.getNoticeId()), convert);
        }

        sendNewMessageCount(sseEmitter);

        return sseEmitter;
    }

    private void sendNewMessageCount(SseEmitter emitter) {
        long count = em.selectFrom(chatHistory)
                .where(chatHistory.receiverId.eq(userUtil.getUser().getUserId()))
                .where(chatHistory.isRead.eq(false))
                .stream().count();

        try {
            emitter.send(SseEmitter.event()
                    .id("")
                    .name("sse")
                    .data(count, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            emitter.completeWithError(exception);
        }
    }

    private void sendNotification(SseEmitter emitter, String eventId, NoticeResponse data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(data.getNoticeId().toString())
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
                        .user(notice.getUser())
                        .url(null)
                        .isRead(notice.isRead())
                        .createAt(notice.getCreateAt())
                        .build();
            }

            case COMMENT, LIKE, FOLLOW, COMMENT_REPLY -> {
                return NoticeResponse.builder()
                        .noticeId(notice.getNoticeId())
                        .noticeType(notice.getNoticeType())
                        .user(notice.getUser())
                        .url(notice.getUrl())
                        .isRead(notice.isRead())
                        .createAt(notice.getCreateAt())
                        .build();
            }

            default -> throw new BasicException(ErrorCode.BAD_REQUEST);
        }
    }
}
