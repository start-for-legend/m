package com.minsta.m.domain.chat.service;

import com.minsta.m.domain.chat.controller.data.request.Message;

public interface ChatSaveService {

    void execute(Message message);
}
