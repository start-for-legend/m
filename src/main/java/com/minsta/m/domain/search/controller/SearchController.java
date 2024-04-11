package com.minsta.m.domain.search.controller;

import com.minsta.m.domain.search.service.GetSearchByKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final GetSearchByKeywordService getSearchByKeywordService;

    @GetMapping
    public ResponseEntity<?> getSearch(@RequestParam(name = "keyword") String keyword) {
        var response = getSearchByKeywordService.execute(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
