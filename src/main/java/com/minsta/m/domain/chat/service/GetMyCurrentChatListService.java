package com.minsta.m.domain.chat.service;

import com.minsta.m.domain.chat.controller.data.response.ChatRoomResponse;

import java.util.List;

public interface GetMyCurrentChatListService {

    List<ChatRoomResponse> execute();
}
