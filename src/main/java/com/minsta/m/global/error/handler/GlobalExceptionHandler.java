package com.minsta.m.global.error.handler;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(HttpServletRequest request, BasicException e) {
        printError(request, e.getErrorCode().getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getErrorCode().getMessage(), e.getErrorCode().getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    private void printError(HttpServletRequest request, String message) {
        log.error(request.getRequestURI());
        log.error(message);
    }
}
