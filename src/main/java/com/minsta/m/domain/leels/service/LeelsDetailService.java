package com.minsta.m.domain.leels.service;

import com.minsta.m.domain.leels.controller.data.response.LeelsResponse;

public interface LeelsDetailService {

    LeelsResponse execute(Long leelsId);
}
