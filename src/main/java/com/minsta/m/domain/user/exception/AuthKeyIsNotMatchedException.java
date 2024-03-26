package com.minsta.m.domain.user.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class AuthKeyIsNotMatchedException extends BasicException {

    public AuthKeyIsNotMatchedException() {
        super(ErrorCode.AUTH_KEY_IS_NOT_MATCHED);
    }
}
