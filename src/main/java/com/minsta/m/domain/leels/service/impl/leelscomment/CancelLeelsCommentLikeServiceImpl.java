package com.minsta.m.domain.leels.service.impl.leelscomment;

import com.minsta.m.domain.leels.entity.Leels;
import com.minsta.m.domain.leels.entity.LeelsComment;
import com.minsta.m.domain.leels.entity.LeelsCommentEmbedded;
import com.minsta.m.domain.leels.entity.LeelsCommentLike;
import com.minsta.m.domain.leels.repository.LeelsCommentLikeRepository;
import com.minsta.m.domain.leels.service.leelscomment.CancelLeelsCommentLikeService;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.global.annotation.ServiceWithTransactional;
import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;
import com.minsta.m.global.util.LeelsCommentUtil;
import com.minsta.m.global.util.LeelsUtil;
import com.minsta.m.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ServiceWithTransactional
public class CancelLeelsCommentLikeServiceImpl implements CancelLeelsCommentLikeService {

    private final UserUtil userUtil;
    private final LeelsUtil leelsUtil;
    private final LeelsCommentUtil leelsCommentUtil;
    private final LeelsCommentLikeRepository leelsCommentLikeRepository;

    @Override
    public void execute(Long leelsId, Long leelsCommentId) {

        User currentUser = userUtil.getUser();
        Leels leels = leelsUtil.getLeels(leelsId);
        LeelsComment leelsComment = leelsCommentUtil.getComment(leelsCommentId);

        LeelsCommentLike leelsCommentLike = leelsCommentLikeRepository.findById(new LeelsCommentEmbedded(
                currentUser.getUserId(),
                leels.getLeelsId(),
                leelsComment.getLeelsCommentId()
        )).orElseThrow(() -> new BasicException(ErrorCode.LEELS_NOT_LIKE));

        leelsCommentLikeRepository.delete(leelsCommentLike);
    }
}
