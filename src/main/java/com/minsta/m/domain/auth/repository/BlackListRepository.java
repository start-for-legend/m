package com.minsta.m.domain.auth.repository;

import com.minsta.m.domain.auth.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, Long> {

    boolean existsByAccessToken(String accessToken);

    boolean existsByRefreshToken(String refreshToken);
}
