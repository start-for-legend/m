package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.response.CreateRoomResponse;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.CreateRoomService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.security.exception.UserNotFoundException;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@ServiceWithTransactional
public class CreateRoomServiceImpl implements CreateRoomService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public CreateRoomResponse execute(Long otherUserId) {

        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(UserNotFoundException::new);
        if (chatRoomRepository.existsByUserAndOtherUserId(userUtil.getUser(), otherUser.getUserId())) {
            throw new BasicException(ErrorCode.EXIST_CHAT_ROOM);
        }
        if (chatRoomRepository.existsByUserAndOtherUserId(otherUser, userUtil.getUser().getUserId())) {
            throw new BasicException(ErrorCode.EXIST_CHAT_ROOM);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .user(userUtil.getUser())
                .otherUserId(otherUserId)
                .lastMessage("")
                .lastMessageTime(ZonedDateTime.now())
                .chatHistories(new ArrayList<>())
                .build();

        chatRoomRepository.save(chatRoom);

        return CreateRoomResponse.builder()
                .roomId(chatRoom.getChatRoomId())
                .build();
    }
}
