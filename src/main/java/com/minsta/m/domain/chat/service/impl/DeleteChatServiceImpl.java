package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.service.DeleteChatService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransactional
public class DeleteChatServiceImpl implements DeleteChatService {

    private final UserUtil userUtil;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(UUID roomId, Long chatId) {

        User currentUser = userUtil.getUser();
        ChatHistory chatHistory = chatHistoryRepository.findById(chatId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_NOT_FOUND));
        if (chatHistory.getChatRoom().getChatRoomId() != roomId) {
            throw new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        if (!chatHistory.getSender().equals(currentUser)) {
            throw new BasicException(ErrorCode.PERMISSION_DENIED_DELETE_CHAT);
        }

        chatHistoryRepository.delete(chatHistory);
    }
}
