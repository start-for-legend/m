package com.minsta.m.domain.user.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class NotAuthenticatedPhoneException extends BasicException {

    public NotAuthenticatedPhoneException() {
        super(ErrorCode.NOT_AUTHENTICATION_PHONE);
    }
}
