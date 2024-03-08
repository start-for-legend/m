package com.minsta.m.domain.file.exception;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorCode;

public class NotAllowedFileException extends BasicException {

    public NotAllowedFileException() {
        super(ErrorCode.NOT_ALLOWED_FILE);
    }
}
