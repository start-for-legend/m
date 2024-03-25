package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.ChatReadCheckService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransactional
public class ChatReadCheckServiceImpl implements ChatReadCheckService {

    private final UserUtil userUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(UUID loomId, Long lastChatId) {

        ChatRoom chatRoom = chatRoomRepository.findById(loomId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        if (!chatRoom.getUser().equals(userUtil.getUser()) && !chatRoom.getOtherUserId().equals(userUtil.getUser().getUserId())) {
            throw new BasicException(ErrorCode.PERMISSION_DENIED_CHAT_ROOM);
        }

        List<ChatHistory> histories = chatHistoryRepository.findAllByChatRoomAndChatIdIsAfter(chatRoom, lastChatId);
        chatHistoryRepository.saveAll(histories.stream()
                .map(this::setIsRead)
                .toList());
    }

    private ChatHistory setIsRead(ChatHistory chatHistory) {
        chatHistory.setRead(true);
        return chatHistory;
    }
}
