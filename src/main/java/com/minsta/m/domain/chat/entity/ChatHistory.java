package com.minsta.m.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private boolean modify;

    @Column(name = "is_read")
    @JsonIgnore
    private boolean isRead;

    public void setContent(String content) {
        this.content = content;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
