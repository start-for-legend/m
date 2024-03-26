package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatHistoryRepository;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.MessageReadService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.UserUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.minsta.m.domain.chat.entity.QChatHistory.chatHistory;

@RequiredArgsConstructor
@ServiceWithTransactional
public class MessageReadServiceImpl implements MessageReadService {

    private final UserUtil userUtil;
    private final JPAQueryFactory em;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public void execute(UUID roomId, Long lastReadId) {
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new BasicException(ErrorCode.CHAT_ROOM_NOT_FOUND));
        if (!room.getOtherUserId().equals(userUtil.getUser().getUserId()) && !room.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.PERMISSION_DENIED_CHAT_ROOM);
        }

        chatHistoryRepository.saveAll(em.selectFrom(chatHistory)
                .where(chatHistory.receiverId.eq(userUtil.getUser().getUserId()))
                .where(chatHistory.isRead.notIn(true))
                .where(chatHistory.chatId.gt(lastReadId))
                .stream().map(this::setRead)
                .collect(Collectors.toList()));

    }

    private ChatHistory setRead(ChatHistory chatHistory) {
        chatHistory.setRead(true);
        return chatHistory;
    }
}
