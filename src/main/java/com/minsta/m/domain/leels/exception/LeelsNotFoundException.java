package com.minsta.m.domain.leels.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class LeelsNotFoundException extends BasicException {

    public LeelsNotFoundException() {
        super(ErrorCode.LEELS_NOT_FOUND);
    }
}
