package com.minsta.m.domain.chat.controller;

import com.minsta.m.domain.chat.controller.data.request.ChatUpdateRequest;
import com.minsta.m.domain.chat.controller.data.response.ChatResponse;
import com.minsta.m.domain.chat.controller.data.response.ChatRoomResponse;
import com.minsta.m.domain.chat.controller.data.response.CreateRoomResponse;
import com.minsta.m.domain.chat.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomService createRoomService;
    private final GetMyCurrentChatListService getMyCurrentChatListService;
    private final GetAllChatHistoryService getAllChatHistoryService;
    private final ChatUpdateService chatUpdateService;
    private final DeleteChatService deleteChatService;

    @PostMapping("/{otherUserId}")
    public ResponseEntity<CreateRoomResponse> createRoom(@PathVariable Long otherUserId) {
        var response = createRoomService.execute(otherUserId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getCurrentMyChatRoomList() {
        var response = getMyCurrentChatListService.execute();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatResponse>> getRoomChatList(@PathVariable UUID roomId) {
        var response = getAllChatHistoryService.execute(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{roomId}/{chatId}")
    public ResponseEntity<HttpStatus> updateChat(
            @PathVariable Long chatId,
            @PathVariable UUID roomId,
            @RequestBody @Valid ChatUpdateRequest chatUpdateRequest
    ) {
        chatUpdateService.execute(roomId, chatId, chatUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{roomId}/{chatId}")
    public ResponseEntity<HttpStatus> deleteChat(
            @PathVariable Long chatId,
            @PathVariable UUID roomId
    ) {
        deleteChatService.execute(roomId, chatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
