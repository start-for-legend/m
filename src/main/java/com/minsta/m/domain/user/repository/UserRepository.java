package com.minsta.m.domain.user.repository;

import com.minsta.m.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);

    boolean existsByNickName(String nickName);

    Optional<User> findByPhone(String phone);

    Optional<User> findByName(String name);
}
