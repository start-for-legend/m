package com.minsta.m.domain.leels.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class CommentUpdatePermissionDeniedException extends BasicException {

    public CommentUpdatePermissionDeniedException() {
        super(ErrorCode.UPDATE_PERMISSION_DENIED);
    }
}
