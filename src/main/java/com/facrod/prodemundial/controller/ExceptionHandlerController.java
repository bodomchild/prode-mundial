package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
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
        var error = new ErrorDTO();
        error.setStatus(BAD_REQUEST.getReasonPhrase());
        error.setErrors(new ArrayList<>());

        if (e.getGlobalErrorCount() > 0) {
            var globalErrors = e.getGlobalErrors().stream().map(err -> {
                var globalError = new ErrorDTO.FieldError();
                globalError.setField(err.getObjectName());
                globalError.setMessage(err.getDefaultMessage());
                return globalError;
            }).collect(Collectors.toList());
            error.getErrors().addAll(globalErrors);
        }

        if (e.getFieldErrorCount() > 0) {
            var fieldErrors = e.getFieldErrors().stream().map(err -> {
                var fieldError = new ErrorDTO.FieldError();
                fieldError.setField(err.getField());
                fieldError.setMessage(err.getDefaultMessage());
                return fieldError;
            }).collect(Collectors.toList());
            error.getErrors().addAll(fieldErrors);
        }

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

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(AccessDeniedException e) {
        // Se relanza la excepcion para que el error sea manejado por el CustomAccessDeniedHandler
        throw e;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(Exception e) {
        var error = new ErrorDTO();
        error.setStatus(INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setError(e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }

}
