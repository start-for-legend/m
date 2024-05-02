package com.minsta.m.domain.feed.entity.feed;

import com.minsta.m.domain.feed.entity.feedcomment.FeedComment;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Feed extends BaseEntity {

    @Id
    @Column(name = "feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "hashtags")
    @ElementCollection
    @CollectionTable(name = "feed_hashtags", joinColumns = @JoinColumn(name = "feed_id"))
    @NotEmpty
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    @Column(name = "fireUrls")
    @ElementCollection
    @CollectionTable(name = "feed_file_urls", joinColumns = @JoinColumn(name = "feed_id"))
    @NotEmpty
    @Builder.Default
    private List<String> fileUrls = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<FeedComment> comments = new ArrayList<>();
}
