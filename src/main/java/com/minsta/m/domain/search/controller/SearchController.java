package com.minsta.m.domain.search.controller;

import com.minsta.m.domain.search.controller.data.response.SearchResponse;
import com.minsta.m.domain.search.service.GetSearchByKeywordService;
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

@Tag(name = "http://10.53.68.120:80/search 하위 API", description = "search 관련 API")
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final GetSearchByKeywordService getSearchByKeywordService;

    @Operation(summary = "search", description = "검색 탭 데이터 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "검색 탭 데이터 가져옴", content = @Content(schema = @Schema(implementation = SearchResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "Token InValid, Token Expired"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, 서버 에러")
    })
    @GetMapping
    public ResponseEntity<SearchResponse> getSearch(@RequestParam(name = "keyword") String keyword) {
        var response = getSearchByKeywordService.execute(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
