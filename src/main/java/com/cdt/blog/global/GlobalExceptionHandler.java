package com.cdt.blog.global;

import com.cdt.blog.exception.BlogException;
import com.cdt.blog.model.comm.Results;
import com.cdt.blog.model.enums.ErrorInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/12/2 22:42
 * @Description:
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Results signatureExceptionHandler(SignatureException exception) {
        log.error("SignatureException:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.TOKEN_INVALID);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Results ValidationExceptionsHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Results.error(errors);
    }

    @ResponseBody
    @ExceptionHandler(value = BlogException.class)
    @ResponseStatus(HttpStatus.OK)
    public Results blogExceptionHandler(BlogException exception) {
        log.error("BlogException:{}", exception.getMessage());
        return exception.toResultVO();
    }

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Results noHandlerFoundExceptionHandler(NoHandlerFoundException exception) {
        log.error("NoHandlerFoundException:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.RESOURCES_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Results exceptionHandler(Exception exception) {
        exception.printStackTrace();
        log.error("Exception:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.UNKNOWN_ERROR);
    }
}
