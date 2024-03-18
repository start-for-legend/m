package com.minsta.m.global.interceptor;

import com.minsta.m.global.security.jwt.TokenParser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompPreHandler implements ChannelInterceptor {

    private final TokenParser tokenParser;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new MessageDeliveryException("Authorization header is missing or blank");
        }

        String token = authorizationHeader.split(" ")[1];
        Authentication authentication = tokenParser.authentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return message;
    }
}
