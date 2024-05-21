package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.DeleteChatService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@ServiceWithTransactional
public class DeleteChatServiceImpl implements DeleteChatService {

    private final UserUtil userUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(UUID roomId, Long chatId) {

        User currentUser = userUtil.getUser();
        ChatHistory chatHistory = chatHistoryRepository.findById(chatId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        if (!chatHistory.getSender().equals(currentUser)) {
            throw new BasicException(ErrorCode.PERMISSION_DENIED_DELETE_CHAT);
        }

        isChange(chatRoom, chatHistory);
        chatHistoryRepository.delete(chatHistory);
    }

    private void isChange(ChatRoom chatRoom, ChatHistory chatHistory) {
        var list = chatHistoryRepository.findAllByChatRoomOrderByCreatedAt(chatRoom);
        ChatHistory last = list.get(list.size() - 1);

        if (
                last.getCreatedAt().isEqual(chatHistory.getCreatedAt()) &&
                        last.getContent().equals(chatHistory.getContent()) &&
                        list.size() > 2
        ) {
            ChatHistory newLast = list.get(list.size() - 2);
            chatRoom.setLastMessage(newLast.getContent());
            chatRoom.setLastMessageTime(newLast.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")));
        } else {
            chatRoom.setLastMessage("");
            chatRoom.setLastMessage(null);
        }
    }
}
