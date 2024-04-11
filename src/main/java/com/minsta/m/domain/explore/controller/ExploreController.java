package com.minsta.m.domain.explore.controller;

import com.minsta.m.domain.explore.controller.response.ExploreResponse;
import com.minsta.m.domain.explore.service.ExploreService;
import com.minsta.m.domain.user.controller.data.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "http://10.53.68.120:80/explore 하위 API", description = "explore 관련 API")
@RestController
@RequestMapping("/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final ExploreService exploreService;

    @Operation(summary = "explore", description = "탐색 탭 데이터 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탐색 탭 데이터 가져옴", content = @Content(schema = @Schema(implementation = ExploreResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<ExploreResponse> get(@RequestParam(value = "page", defaultValue = "0") int page) {
        var response = exploreService.execute(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
