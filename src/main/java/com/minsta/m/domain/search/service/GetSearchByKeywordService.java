package com.minsta.m.domain.search.service;

import com.minsta.m.domain.search.controller.data.response.SearchResponse;

public interface GetSearchByKeywordService {

    SearchResponse execute(String keyword);
}
