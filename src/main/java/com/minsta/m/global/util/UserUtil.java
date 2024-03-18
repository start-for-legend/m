package com.minsta.m.global.util;

import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.security.auth.AuthDetails;
import com.minsta.m.global.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthDetails authDetails = null;
        if (authentication.getPrincipal() instanceof AuthDetails) {
            authDetails = (AuthDetails) authentication.getPrincipal();
        }
        if (authDetails == null || authDetails.getPhone() == null) {
            throw new BasicException(ErrorCode.SERVER_ERROR);
        }

        return userRepository.findByPhone(authDetails.getPhone()).orElseThrow(UserNotFoundException::new);
    }
}
