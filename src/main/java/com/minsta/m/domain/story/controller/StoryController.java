package com.minsta.m.domain.story.controller;

import com.minsta.m.domain.story.controller.data.request.CreateStoryRequest;
import com.minsta.m.domain.story.controller.data.response.StoryResponse;
import com.minsta.m.domain.story.service.CreateStoryService;
import com.minsta.m.domain.story.service.StoryReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "http://10.53.68.120:80/leels 하위 API", description = "Leels 관련 API")
@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {

    private final CreateStoryService createStoryService;
    private final StoryReadService storyReadService;

    @Operation(summary = "create story", description = "스토리 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "스토리 생성",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createStory(@RequestBody @Valid CreateStoryRequest createStoryRequest) {
        createStoryService.execute(createStoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "story reed", description = "스토리 보기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    headers = @Header(name = "accessToken", description = "accessToken value", required = true)),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token Expired, Token Invalid"),
            @ApiResponse(responseCode = "404", description = "NotFoundStory"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping("/{storyId}")
    public ResponseEntity<StoryResponse> readStory(@PathVariable Long storyId) {
        var response = storyReadService.execute(storyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
