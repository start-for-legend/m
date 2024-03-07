package com.minsta.m.global.security.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class TokenExpiredException extends BasicException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_IS_EXPIRED);
    }
}
