package com.minsta.m.domain.leels.service.impl.leels;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.repository.LeelsRepository;
import com.minsta.m.domain.leels.service.leels.LeelsDeleteService;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServiceWithTransactional
public class LeelsDeleteServiceImpl implements LeelsDeleteService {

    private final LeelsUtil leelsUtil;
    private final UserUtil userUtil;
    private final LeelsRepository leelsRepository;

    @Override
    public void execute(Long leelsId) {

        Leels leels = leelsUtil.getLeels(leelsId);
        if (!leels.getUser().equals(userUtil.getUser())) {
            throw new BasicException(ErrorCode.DELETE_LEELS_DENIED);
        }

        leelsRepository.delete(leels);
    }
}
