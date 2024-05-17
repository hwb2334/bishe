package com.shenmou.springboot.exception;

import com.shenmou.springboot.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public Result customExceptionHandler(ServiceException se) {
        return Result.error(se.getCode(), se.getMessage());
    }
}
