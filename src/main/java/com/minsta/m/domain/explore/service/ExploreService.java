package com.minsta.m.domain.explore.service;

import com.minsta.m.domain.explore.controller.response.ExploreResponse;

public interface ExploreService {

    ExploreResponse execute(int page);
}
