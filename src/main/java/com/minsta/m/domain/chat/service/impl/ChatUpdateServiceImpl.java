package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.request.ChatUpdateRequest;
import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.ChatUpdateService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransactional
public class ChatUpdateServiceImpl implements ChatUpdateService {

    private final UserUtil userUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(UUID roomId, Long chatId, ChatUpdateRequest chatUpdateRequest) {

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        User currentUser = userUtil.getUser();
        if (room.getUser() != currentUser && room.getOtherUserId().equals(currentUser.getUserId())) {
            throw new BasicException(ErrorCode.PERMISSION_DENIED_CHAT_ROOM);
        }

        ChatHistory chatHistory = chatHistoryRepository.findById(chatId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_NOT_FOUND));

        chatHistory.setContent(chatUpdateRequest.getContent());
        chatHistory.setModify(true);

        chatHistoryRepository.save(chatHistory);
    }
}
