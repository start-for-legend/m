package com.minsta.m.domain.leels.entity;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Leels extends BaseEntity {

    @Id
    @Column(name = "leels_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leelsId;

    @Column(name = "content", length = 100)
    private String content;

    @Column(name = "hashtags")
    @ElementCollection
    @CollectionTable(name = "leels_hashtags", joinColumns = @JoinColumn(name = "leels_id"))
    @NotEmpty
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    @Column(nullable = false, name = "leels_url")
    private String leelsUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
