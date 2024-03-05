package com.minsta.m.domain.user.repository;

import com.minsta.m.domain.user.entity.SmsAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsAuthRepository extends JpaRepository<SmsAuthentication, Long> {

    boolean existsByPhone(String phone);

    SmsAuthentication findByPhone(String phone);
}
