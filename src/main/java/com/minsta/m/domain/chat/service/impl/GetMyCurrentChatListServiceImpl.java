package com.minsta.m.domain.chat.service.impl;

import com.minsta.m.domain.chat.controller.data.response.ChatRoomResponse;
import com.minsta.m.domain.chat.entity.ChatRoom;
import com.minsta.m.domain.chat.repository.ChatRoomRepository;
import com.minsta.m.domain.chat.service.GetMyCurrentChatListService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.UserRepository;
import com.minsta.m.global.annotation.ReadOnlyService;
import com.minsta.m.global.security.exception.UserNotFoundException;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@ReadOnlyService
@RequiredArgsConstructor
public class GetMyCurrentChatListServiceImpl implements GetMyCurrentChatListService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public List<ChatRoomResponse> execute() {
        User currentUser = userUtil.getUser();
        List<ChatRoom> chatRooms = chatRoomRepository.findByUserOrOtherUserIdOrderByLastMessageTimeDesc(currentUser, currentUser.getUserId());

        return chatRooms.stream()
                .map(chatRoom -> createChatRoomResponse(chatRoom, currentUser))
                .collect(Collectors.toList());
    }

    private ChatRoomResponse createChatRoomResponse(ChatRoom chatRoom, User currentUser) {
        Long opponentId = Objects.equals(chatRoom.getOtherUserId(), currentUser.getUserId()) ? chatRoom.getUser().getUserId() : chatRoom.getOtherUserId();
        String opponentNickName = fetchNickName(opponentId);
        String opponentProfileUrl = fetchProfileUrl(opponentId);

        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .opponentId(opponentId)
                .opponentNickName(opponentNickName)
                .opponentProfileUrl(opponentProfileUrl)
                .lastMessage(chatRoom.getLastMessage())
                .lastMessageTime(chatRoom.getLastMessageTime())
                .build();
    }

    private String fetchNickName(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getNickName();
    }

    private String fetchProfileUrl(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new).getProfileUrl();
    }

}
