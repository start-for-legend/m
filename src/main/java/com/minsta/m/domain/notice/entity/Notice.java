package com.minsta.m.domain.notice.entity;

import com.minsta.m.domain.notice.entity.enums.NoticeType;
import com.minsta.m.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "notice_type")
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    @Column(nullable = false, name = "is_read")
    private boolean isRead;

    @Column(nullable = false, name = "is_valid")
    private boolean isValid;

    @Column(nullable = false, name = "create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(nullable = true, name = "nav_url")
    private String url;
}
