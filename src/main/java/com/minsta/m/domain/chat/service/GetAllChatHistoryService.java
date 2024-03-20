package com.minsta.m.domain.chat.service;

import com.minsta.m.domain.chat.controller.data.response.ChatResponse;

import java.util.List;
import java.util.UUID;

public interface GetAllChatHistoryService {

    List<ChatResponse> execute(UUID roomId);
}
