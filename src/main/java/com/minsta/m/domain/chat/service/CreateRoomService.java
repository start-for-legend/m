package com.minsta.m.domain.chat.service;

import com.minsta.m.domain.chat.controller.data.response.CreateRoomResponse;

public interface CreateRoomService {

    CreateRoomResponse execute(Long otherUserId);
}
