package com.minsta.m.domain.leels.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class CommentDeletePermissionDeniedException extends BasicException {

    public CommentDeletePermissionDeniedException() {
        super(ErrorCode.DELETE_PERMISSION_DENIED);
    }
}
