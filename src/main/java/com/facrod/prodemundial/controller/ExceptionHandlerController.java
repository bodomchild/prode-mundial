package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDTO> appExceptionHandler(AppException e) {
        var error = new ErrorDTO();
        error.setStatus(e.getStatus().getReasonPhrase());
        error.setError(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        var fieldErrors = e.getFieldErrors().stream().map(err -> {
            var fieldError = new ErrorDTO.FieldError();
            fieldError.setField(err.getField());
            fieldError.setMessage(err.getDefaultMessage());
            return fieldError;
        }).collect(Collectors.toList());

        var error = new ErrorDTO();
        error.setStatus(BAD_REQUEST.getReasonPhrase());
        error.setErrors(fieldErrors);

        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

}
