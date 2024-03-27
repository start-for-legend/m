package com.minsta.m.domain.follow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class FollowEmbedded implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "follower_id")
    private Long followerId;
}
