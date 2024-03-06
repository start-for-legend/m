package com.minsta.m.domain.user.service;

import com.minsta.m.domain.user.controller.data.request.SmsSendRequest;
import com.minsta.m.domain.user.entity.SmsAuthentication;
import com.minsta.m.domain.user.repository.SmsAuthRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SmsSendTest {

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private SmsAuthRepository smsAuthRepository;

    @Test
    @DisplayName("success")
    void success_send() {
        SmsSendRequest sms = new SmsSendRequest(
                "01037706712"
        );

        smsSendService.execute(sms);

        SmsAuthentication auth = smsAuthRepository.findByPhone(sms.getPhone());

        Assertions.assertEquals(sms.getPhone(), auth.getPhone());
    }
}
