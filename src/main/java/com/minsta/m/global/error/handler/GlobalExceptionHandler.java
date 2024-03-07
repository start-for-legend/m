package com.minsta.m.global.error.handler;

import com.minsta.m.global.error.BasicException;
import com.minsta.m.global.error.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(HttpServletRequest request, BasicException e) {
        printError(request, e.getErrorCode().getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getErrorCode().getMessage(), e.getErrorCode().getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> validateException(MethodArgumentNotValidException e) {
        log.warn("Validation Failed : {}", e.getMessage());
        log.trace("Validation Failed Details : ", e);
        return new ResponseEntity<>(new ErrorMessage(methodArgumentNotValidExceptionToJson(e), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> unExpectedException(RuntimeException e) {
        log.error("UnExpectedException Occur : ", e);
        return new ResponseEntity<>(new ErrorMessage("Internal Server Error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> noHandlerFoundException(NoHandlerFoundException e) {
        log.warn("Not Found Endpoint : {}", e.getMessage());
        log.trace("Not Found Endpoint Details : ", e);
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), 404), HttpStatus.FORBIDDEN);
    }

    private static String methodArgumentNotValidExceptionToJson(MethodArgumentNotValidException e) {
        Map<String, Object> globalResults = new HashMap<>();
        Map<String, String> fieldResults = new HashMap<>();

        e.getBindingResult().getGlobalErrors().forEach(error -> globalResults.put(e.getBindingResult().getObjectName(), error.getDefaultMessage()));
        e.getBindingResult().getFieldErrors().forEach(error -> fieldResults.put(error.getField(), error.getDefaultMessage()));
        globalResults.put(e.getBindingResult().getObjectName(), fieldResults);

        return new JSONObject(globalResults).toString().replace("\"", "'");
    }

    private void printError(HttpServletRequest request, String message) {
        log.error(request.getRequestURI());
        log.error(message);
    }
}
