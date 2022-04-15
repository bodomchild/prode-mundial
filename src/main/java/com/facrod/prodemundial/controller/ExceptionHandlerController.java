package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorDTO> invalidFormatExceptionHandler(InvalidFormatException e) {
        var error = new ErrorDTO();
        error.setStatus(BAD_REQUEST.getReasonPhrase());
        error.setError("Error al deserializar '" + e.getValue() + "' a la clase '" + e.getTargetType().getSimpleName() + "'");
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDTO> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        var error = new ErrorDTO();
        error.setStatus(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        error.setError("Content-Type '" + e.getContentType() + "' no soportado. Esperado: '" + e.getSupportedMediaTypes() + "'");
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDTO> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        var error = new ErrorDTO();
        error.setStatus(NOT_FOUND.getReasonPhrase());
        error.setError("No se encontro el recurso '" + e.getRequestURL() + "'");
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

}
