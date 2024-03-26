package com.minsta.m.domain.chat.service;

import java.util.UUID;

public interface MessageReadService {

    void execute(UUID roomId, Long lastReadId);
}
