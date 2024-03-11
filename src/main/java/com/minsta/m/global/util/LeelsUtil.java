package com.minsta.m.global.util;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.exception.LeelsNotFoundException;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeelsUtil {

    private final LeelsRepository leelsRepository;

    public Leels getLeels(Long leelsId) {
        return leelsRepository.findById(leelsId).orElseThrow(LeelsNotFoundException::new);
    }
}
