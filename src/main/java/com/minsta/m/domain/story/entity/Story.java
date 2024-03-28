package com.minsta.m.domain.story.entity;

import com.minsta.m.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Story {

    @Id
    @Column(name = "story_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "story_url", nullable = false)
    private String url;

    @Column(name = "watchers")
    @ElementCollection
    @CollectionTable(name = "leels_watchers", joinColumns = @JoinColumn(name = "story_id"))
    @Builder.Default
    private List<Long> watchers = new ArrayList<>();

    @Column(nullable = false, name = "is_valid")
    private boolean isValid;

    @CreatedDate
    private LocalDateTime createdAt;

    public void addWatchers(Long userId) {
        watchers.add(userId);
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }
}
