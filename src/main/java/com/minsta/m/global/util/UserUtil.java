package com.minsta.m.global.util;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(name).orElseThrow(UserNotFoundException::new);
    }
}
