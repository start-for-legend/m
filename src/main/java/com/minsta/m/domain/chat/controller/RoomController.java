package com.minsta.m.domain.chat.controller;

import com.minsta.m.domain.chat.controller.data.request.ChatUpdateRequest;
import com.minsta.m.domain.chat.controller.data.response.ChatResponse;
import com.minsta.m.domain.chat.controller.data.response.ChatRoomResponse;
import com.minsta.m.domain.chat.controller.data.response.CreateRoomResponse;
import com.minsta.m.domain.chat.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "http://10.53.68.120:80/room 하위 API", description = "Room 관련 API")
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomService createRoomService;
    private final GetMyCurrentChatListService getMyCurrentChatListService;
    private final GetAllChatHistoryService getAllChatHistoryService;
    private final ChatUpdateService chatUpdateService;
    private final DeleteChatService deleteChatService;
    private final MessageReadService messageReadService;

    @Operation(summary = "create room", description = "채팅방 생성")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PostMapping("/{otherUserId}")
    public ResponseEntity<CreateRoomResponse> createRoom(@PathVariable Long otherUserId) {
        var response = createRoomService.execute(otherUserId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "get current my chat room list", description = "자신의 채팅방 불러오기")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ChatRoomResponse.class))
                    ),
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getCurrentMyChatRoomList() {
        var response = getMyCurrentChatListService.execute();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "get room chat list", description = "채팅방 채팅 리스트 불러오기")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ChatResponse.class))
                    ),
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not in Chat Room"),
            @ApiResponse(responseCode = "404", description = "Room Not Found"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatResponse>> getRoomChatList(@PathVariable UUID roomId) {
        var response = getAllChatHistoryService.execute(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "chat read service", description = "채팅방 채팅 읽음 처리")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not in Chat Room"),
            @ApiResponse(responseCode = "404", description = "Room Not Found"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PatchMapping("/{roomId}/{lastReadId}/re")
    public ResponseEntity<HttpStatus> changeChatRead(@PathVariable UUID roomId, @PathVariable Long lastReadId) {
        messageReadService.execute(roomId, lastReadId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "chat update service", description = "채팅 업데이트")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not in Chat Room"),
            @ApiResponse(responseCode = "404", description = "Room Not Found"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @PatchMapping("/{roomId}/{chatId}")
    public ResponseEntity<HttpStatus> updateChat(
            @PathVariable Long chatId,
            @PathVariable UUID roomId,
            @RequestBody @Valid ChatUpdateRequest chatUpdateRequest
    ) {
        chatUpdateService.execute(roomId, chatId, chatUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "delete chat", description = "채팅방 채팅 삭제")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "OK",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ChatResponse.class))
                    ),
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "403", description = "Not in Chat Room"),
            @ApiResponse(responseCode = "404", description = "Room Not Found"),
            @ApiResponse(responseCode = "500", description = "Server Error"),
    })
    @DeleteMapping("/{roomId}/{chatId}")
    public ResponseEntity<HttpStatus> deleteChat(
            @PathVariable Long chatId,
            @PathVariable UUID roomId
    ) {
        deleteChatService.execute(roomId, chatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
