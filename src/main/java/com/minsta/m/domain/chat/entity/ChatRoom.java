package com.minsta.m.domain.chat.entity;

import com.minsta.m.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID chatRoomId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long otherUserId;

    @Column(name = "last_message", length = 100)
    private String lastMessage;

    private ZonedDateTime lastMessageTime;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatRoom")
    List<ChatHistory> chatHistories = new ArrayList<>();

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastMessageTime(ZonedDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
