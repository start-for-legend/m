package com.minsta.m.domain.file.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class InvalidFormatFileException extends BasicException {

    public InvalidFormatFileException() {
        super(ErrorCode.INVALID_FORMAT_FILE);
    }
}
