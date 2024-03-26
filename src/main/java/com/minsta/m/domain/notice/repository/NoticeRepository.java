package com.minsta.m.domain.notice.repository;

import com.minsta.m.domain.notice.entity.Notice;
import com.minsta.m.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByUserAndNoticeIdAfter(User user, Long noticeId);
}
