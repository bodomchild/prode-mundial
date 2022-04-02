package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDTO> appExceptionHandler(AppException e) {
        var error = new ErrorDTO();
        error.setStatus(e.getStatus().getReasonPhrase());
        error.setError(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(error);
    }

}
