package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.request.SignupRequest;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.exception.DuplicatedNickNameException;
import com.minsta.m.domain.user.exception.DuplicatedPhoneException;
import com.minsta.m.domain.user.exception.NotAuthenticatedPhoneException;
import com.minsta.m.domain.user.repository.SmsAuthRepository;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.domain.user.service.SignupService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
@ServiceWithTransactional
public class SignupServiceImpl implements SignupService {

    private final PasswordEncoder passwordEncoder;
    private final SmsAuthRepository smsAuthRepository;
    private final UserRepository userRepository;

    @Override
    public void execute(SignupRequest signupRequest) {

        if (!smsAuthRepository.findByPhone(signupRequest.getPhone()).isCheck()) {
            throw new NotAuthenticatedPhoneException();
        }

        if (userRepository.existsByPhone(signupRequest.getPhone())) {
            throw new DuplicatedPhoneException();
        }

        if (userRepository.existsByNickName(signupRequest.getNickName())) {
            throw new DuplicatedNickNameException();
        }

        User user = User.builder()
                .phone(signupRequest.getPhone())
                .name(signupRequest.getName())
                .nickName(signupRequest.getNickName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .profileUrl(null)
                .build();

        userRepository.save(user);
    }
}
