package com.minsta.m.domain.chat.repository;

import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    boolean existsByUserAndOtherUserId(User user, Long otherUserId);

    List<ChatRoom> findByUserOrOtherUserIdOrderByLastMessageTimeDesc(User user, Long otherUserId);
}
