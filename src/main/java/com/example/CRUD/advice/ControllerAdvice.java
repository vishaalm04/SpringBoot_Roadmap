package com.example.CRUD.advice;
import com.example.CRUD.exception.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class ControllerAdvice {
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDuplicateKeyException(DuplicateKeyException e) {
        return e.getMessage();
    }

}
