package com.minsta.m.global.security.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class TokenNotValidException extends BasicException {

    public TokenNotValidException() {
        super(ErrorCode.TOKEN_NOT_VALID);
    }
}
