package com.minsta.m.domain.chat.controller.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatResponse {

    private Long userId;

    private String userProfileUrl;

    private String chat;

    private LocalDateTime chatTime;
}
