package com.minsta.m.domain.leels.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class LeelsCommentNotFoundException extends BasicException {

    public LeelsCommentNotFoundException() {
        super(ErrorCode.LEELS_COMMENT_NOT_FOUND);
    }
}
