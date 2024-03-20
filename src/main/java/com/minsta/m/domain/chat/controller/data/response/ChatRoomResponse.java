package com.minsta.m.domain.chat.controller.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomResponse {

    private UUID chatRoomId;

    private Long opponentId;

    private String opponentProfileUrl;

    private String opponentNickName;

    private String lastMessage;

    private ZonedDateTime lastMessageTime;
}
