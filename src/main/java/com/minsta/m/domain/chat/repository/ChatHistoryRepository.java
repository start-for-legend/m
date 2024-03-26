package com.minsta.m.domain.chat.repository;

import com.minsta.m.domain.chat.entity.ChatHistory;
import com.minsta.m.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    List<ChatHistory> findAllByChatRoom(ChatRoom chatRoom);

    List<ChatHistory> findAllByChatRoomAndChatIdIsAfter(ChatRoom chatRoom, Long chatId);
}
