package com.minsta.m.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedisList {

    private final StringRedisTemplate redisTemplate;
    private final String FOLLOWING_LIST_KEY = "followingList";

    public void addFollowing(String userId, String nickname) {
        String userEntry = userId + ":" + nickname;
        redisTemplate.opsForList().rightPush(FOLLOWING_LIST_KEY, userEntry);

        long currentSize = Optional.ofNullable(redisTemplate.opsForList().size(FOLLOWING_LIST_KEY)).orElse(0L);

        long MAX_SIZE = 100;
        if (currentSize > MAX_SIZE) {
            redisTemplate.opsForList().trim(FOLLOWING_LIST_KEY, currentSize - MAX_SIZE, -1);
        }
    }

    public long[] get() {
        List<String> allFollowings = redisTemplate.opsForList().range(FOLLOWING_LIST_KEY, 0, -1);

        Map<String, Long> userIdCounts = allFollowings.stream()
                .map(entry -> entry.split(":")[0])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return userIdCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .mapToLong(entry -> Long.parseLong(entry.getKey()))
                .toArray();
    }
}
