package com.minsta.m.global.util.request;

import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.domain.user.entity.User;

public record NoticeRequest(
        NoticeType noticeType,
        String url,
        User user,
        Long receiverId
) {}
