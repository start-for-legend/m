package com.minsta.m.domain.chat.controller;

import com.minsta.m.domain.chat.controller.data.response.CreateRoomResponse;
import com.minsta.m.domain.chat.service.CreateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomService createRoomService;

    @PostMapping("/{otherUserId}")
    public ResponseEntity<CreateRoomResponse> createRoom(@PathVariable Long otherUserId) {
        var response = createRoomService.execute(otherUserId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
