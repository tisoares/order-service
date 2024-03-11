package com.tisoares.oderservice.external.api.exceptionhandler;

import com.tisoares.oderservice.external.domain.ErrorResponse;
import com.tisoares.oderservice.internal.exception.BaseException;
import com.tisoares.oderservice.internal.exception.BaseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {BaseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(BaseException baseException) {
        logger.error(baseException.getMessage(), baseException);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(baseException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {BaseNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(BaseNotFoundException baseNotFoundException) {
        logger.error(baseNotFoundException.getMessage(), baseNotFoundException);
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(baseNotFoundException.getMessage())
                .build();
    }
}
