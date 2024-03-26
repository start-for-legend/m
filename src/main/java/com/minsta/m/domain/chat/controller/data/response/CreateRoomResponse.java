package com.minsta.m.domain.chat.controller.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateRoomResponse {

    private UUID roomId;
}
