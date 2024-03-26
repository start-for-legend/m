package com.minsta.m.domain.chat.controller;

import com.minsta.m.domain.chat.controller.data.request.Message;
import com.minsta.m.domain.chat.service.ChatSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatSaveService chatSaveService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/chat")
    public void sendMessage(Message message) {
        chatSaveService.execute(message);
        simpMessageSendingOperations.convertAndSend("/sub/" + message.getChatRoomId(), message);
    }
}
