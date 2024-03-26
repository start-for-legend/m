package com.minsta.m.domain.chat.service;

import java.util.UUID;

public interface ChatReadCheckService {

    void execute(UUID loomId, Long chatId);
}
