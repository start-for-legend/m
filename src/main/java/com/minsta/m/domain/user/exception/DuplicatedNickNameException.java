package com.minsta.m.domain.user.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class DuplicatedNickNameException extends BasicException {

    public DuplicatedNickNameException() {
        super(ErrorCode.USER_NICK_NAME_EXIST);
    }
}
