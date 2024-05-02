package com.minsta.m.global.util;

import com.minsta.m.domain.notice.entity.Notice;
import com.minsta.m.domain.notice.repository.NoticeRepository;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateNotice {

    @Value("${url}")
    private String SERVER;
    private final NoticeRepository noticeRepository;

    public void createNotice(NoticeRequest data) {
        Notice notice = Notice.builder()
                .noticeType(data.noticeType())
                .user(data.user())
                .isRead(false)
                .isValid(false)
                .url(SERVER + data.url())
                .createAt(LocalDateTime.now())
                .receiverId(data.receiverId())
                .build();

        noticeRepository.save(notice);
    }
}
