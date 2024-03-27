package com.minsta.m.domain.notice.controller.data.response;

import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.domain.user.controller.data.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {

    private Long noticeId;

    private UserResponse userResponse;

    private NoticeType noticeType;

    private String url;

    private boolean isRead;

    private boolean isValid;

    private LocalDateTime createAt;

}
