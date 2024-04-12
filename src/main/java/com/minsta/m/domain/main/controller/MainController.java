package com.minsta.m.domain.main.controller;

import com.minsta.m.domain.main.controller.data.response.MainResponse;
import com.minsta.m.domain.main.service.MainResponseService;
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
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "http://10.53.68.120:80 Main API", description = "MAIN 관련 API")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final MainResponseService mainResponseService;

    @Operation(summary = "Main", description = "메인 화면 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK, 메인 화면 불러옴", content = @Content(schema = @Schema(implementation = MainResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "400과 같은 이유"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<MainResponse> getMain() {
        var response = mainResponseService.execute();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
