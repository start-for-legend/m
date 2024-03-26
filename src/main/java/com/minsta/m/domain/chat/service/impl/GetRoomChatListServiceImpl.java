package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.response.ChatResponse;
import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.GetAllChatHistoryService;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ReadOnlyService
@RequiredArgsConstructor
public class GetRoomChatListServiceImpl implements GetAllChatHistoryService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public List<ChatResponse> execute(UUID roomId) {
        List<ChatResponse> chatResponses = new ArrayList<>();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        List<ChatHistory> histories = chatHistoryRepository.findAllByChatRoom(chatRoom);
        for (ChatHistory history : histories) {
            LocalDateTime time = history.isModify() ? history.getUpdatedAt() : history.getCreatedAt();

            ChatResponse chatResponse = ChatResponse.builder()
                    .chatId(history.getChatId())
                    .userId(history.getSender().getUserId())
                    .userProfileUrl(history.getSender().getProfileUrl())
                    .chat(history.getContent())
                    .chatTime(time)
                    .build();

            chatResponses.add(chatResponse);
        }

        return chatResponses;
    }
}
