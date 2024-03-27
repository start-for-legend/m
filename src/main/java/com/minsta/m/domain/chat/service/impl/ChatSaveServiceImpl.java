package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.request.Message;
import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.ChatSaveService;
import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.security.exception.UserNotFoundException;
import com.minsta.m.global.util.CreateNotice;
import com.minsta.m.global.util.request.NoticeRequest;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@ServiceWithTransactional
public class ChatSaveServiceImpl implements ChatSaveService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final CreateNotice createNotice;

    @Override
    public void execute(Message message) {

        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId())
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        chatRoom.setLastMessage(message.getMessage());
        chatRoom.setLastMessageTime(ZonedDateTime.now(ZoneId.of("UTC")));

        chatRoomRepository.save(chatRoom);

        long receiverId = !Objects.equals(chatRoom.getOtherUserId(), message.getSenderId()) ?
                chatRoom.getOtherUserId() : chatRoom.getUser().getUserId();

        User user = userRepository.findById(message.getSenderId()).orElseThrow(UserNotFoundException::new);
        ChatHistory chatHistory = ChatHistory.builder()
                .sender(user)
                .content(message.getMessage())
                .chatRoom(chatRoom)
                .modify(false)
                .isRead(false)
                .receiverId(receiverId)
                .build();

        chatHistoryRepository.save(chatHistory);
        createNotice.createNotice(new NoticeRequest(
                NoticeType.MESSAGE,
                "",
                user,
                receiverId
        ));
    }
}
