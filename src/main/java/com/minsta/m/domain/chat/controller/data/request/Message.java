package com.minsta.m.domain.chat.controller.data.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private UUID chatRoomId;

    private Long senderId;

    private String message;
}