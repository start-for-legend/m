package com.minsta.m.domain.follow.service;

import com.minsta.m.domain.follow.controller.data.response.RecommendedFollowerResponse;

import java.util.List;

public interface FollowRecommendedService {

    List<RecommendedFollowerResponse> execute();
}
