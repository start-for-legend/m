package com.minsta.m.domain.chat.entity;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory extends BaseEntity {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;
}
