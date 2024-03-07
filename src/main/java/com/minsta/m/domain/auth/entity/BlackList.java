package com.minsta.m.domain.auth.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash(value = "BlackList")
@NoArgsConstructor
@AllArgsConstructor
public class BlackList {

    @Id
    private Long userId;

    @Indexed
    private String accessToken;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private long expiredIn;
}
