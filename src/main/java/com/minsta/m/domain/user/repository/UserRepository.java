package com.minsta.m.domain.user.repository;

import com.minsta.m.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);

    boolean existsByNickName(String nickName);
}
