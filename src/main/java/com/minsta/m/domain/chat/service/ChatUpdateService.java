package com.minsta.m.domain.chat.service;

import com.minsta.m.domain.chat.controller.data.request.ChatUpdateRequest;

import java.util.UUID;

public interface ChatUpdateService {

    void execute(UUID roomId, Long chatId, ChatUpdateRequest chatUpdateRequest);
}
