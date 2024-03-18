package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.request.Message;
import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.ChatSaveService;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@ServiceWithTransactional
public class ChatSaveServiceImpl implements ChatSaveService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(Message message) {

        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId())
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        chatRoom.setLastMessage(message.getMessage());
        chatRoom.setLastMessageTime(ZonedDateTime.now());

        chatRoomRepository.save(chatRoom);

        ChatHistory chatHistory = ChatHistory.builder()
                .sender(userRepository.findById(message.getSenderId()).orElseThrow(UserNotFoundException::new))
                .content(message.getMessage())
                .chatRoom(chatRoomRepository.getReferenceById(message.getChatRoomId()))
                .build();

        chatHistoryRepository.save(chatHistory);
    }
}
