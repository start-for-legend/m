package com.minsta.m.domain.user.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class DuplicatedPhoneException extends BasicException {

    public DuplicatedPhoneException() {
        super(ErrorCode.USER_PHONE_EXIST);
    }
}
