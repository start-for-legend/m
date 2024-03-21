package com.minsta.m.domain.chat.service;

import java.util.UUID;

public interface DeleteChatService {

    void execute(UUID roomId, Long chatId);
}
