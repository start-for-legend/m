package com.minsta.m.domain.notice.service.impl;

import com.minsta.m.domain.notice.entity.Notice;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.domain.notice.repository.NoticeRepository;
import com.minsta.m.domain.notice.service.NoticeReadService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.minsta.m.domain.notice.entity.QNotice.notice;

@RequiredArgsConstructor
@ServiceWithTransactional
public class NoticeReadServiceImpl implements NoticeReadService {

    private final UserUtil userUtil;
    private final JPAQueryFactory em;
    private final NoticeRepository noticeRepository;

    @Override
    public void execute(Long lastNoticeId) {

        noticeRepository.saveAll(em.selectFrom(notice)
                .where(notice.isRead.notIn(true))
                .where(notice.isValid.notIn(true))
                .where(notice.receiverId.eq(userUtil.getUser().getUserId()))
                .where(notice.noticeType.notIn(NoticeType.MESSAGE)).stream()
                .map(this::setRead).toList());
    }

    private Notice setRead(Notice notice) {
        notice.setRead(true);
        return notice;
    }
}
