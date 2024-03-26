package com.minsta.m.domain.user.service.impl;

import com.minsta.m.domain.user.controller.data.request.SmsSendRequest;
import com.minsta.m.domain.user.entity.SmsAuthentication;
import com.minsta.m.domain.user.repository.SmsAuthRepository;
import com.minsta.m.domain.user.service.SmsSendService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@ServiceWithTransactional
public class SmsSendServiceImpl implements SmsSendService {

    private final SmsAuthRepository smsAuthRepository;

    @Value("${coolsms.api}")
    private String API_KEY;
    @Value("${coolsms.secret}")
    private String API_SECRET;
    @Value("${coolsms.balshin}")
    private String BALSHIN;

    @Override
    public void execute(SmsSendRequest smsSendRequest) {
        int key = new SecureRandom().nextInt(900000) + 100000;

        Message message = new Message(API_KEY, API_SECRET);
        HashMap<String, String> params = new HashMap<>();

        params.put("to", smsSendRequest.getPhone());
        params.put("from", BALSHIN);
        params.put("type", "SMS");
        params.put("text", "가입 암호 : " + key);

        try {
            JSONObject obj = message.send(params);
            log.info("success send message {}", obj);
            save(smsSendRequest.getPhone(), key);
        } catch (CoolsmsException e) {
            log.error("failed send message {}", e.toString());
            throw new BasicException(ErrorCode.SERVER_ERROR);
        }
    }

    private void save(String phone, int key) {
        if (smsAuthRepository.existsByPhone(phone)) {
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        SmsAuthentication auth = SmsAuthentication.builder()
                .phone(phone)
                .key(key)
                .check(false)
                .build();

        smsAuthRepository.save(auth);
    }
}
