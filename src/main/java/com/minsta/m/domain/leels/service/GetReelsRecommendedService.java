package com.minsta.m.domain.leels.service;

import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;

import java.util.List;

public interface GetReelsRecommendedService {

    List<LeelsResponse> execute();
}
