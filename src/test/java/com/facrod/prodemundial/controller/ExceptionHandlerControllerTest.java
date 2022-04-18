package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionHandlerControllerTest {

    private final ExceptionHandlerController controller = new ExceptionHandlerController();

    @Test
    void appExceptionHandler() {
        var exception = new AppException(HttpStatus.NOT_FOUND, "No encontrado");
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setError("No encontrado");
        var expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        var actual = controller.appExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void methodArgumentNotValidExceptionHandler_fieldError() {
        var exception = mock(MethodArgumentNotValidException.class);
        var fieldError = mock(FieldError.class);
        var errorFieldError = new ErrorDTO.FieldError();
        errorFieldError.setField("id");
        errorFieldError.setMessage("El id del partido no puede ser negativo");
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setErrors(List.of(errorFieldError));
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        when(exception.getFieldErrorCount()).thenReturn(1);
        when(exception.getFieldErrors()).thenReturn(List.of(fieldError));
        when(fieldError.getField()).thenReturn("id");
        when(fieldError.getDefaultMessage()).thenReturn("El id del partido no puede ser negativo");

        var actual = controller.methodArgumentNotValidExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void methodArgumentNotValidExceptionHandler_globalError() {
        var exception = mock(MethodArgumentNotValidException.class);
        var globalError = mock(ObjectError.class);
        var errorGlobalError = new ErrorDTO.FieldError();
        errorGlobalError.setField("matchUpdateResultDTO");
        errorGlobalError.setMessage("No puede haber penales si no hubo tiempo extra. Debe ingresar los datos de la ronda de penales");
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setErrors(List.of(errorGlobalError));
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        when(exception.getGlobalErrorCount()).thenReturn(1);
        when(exception.getGlobalErrors()).thenReturn(List.of(globalError));
        when(globalError.getObjectName()).thenReturn("matchUpdateResultDTO");
        when(globalError.getDefaultMessage()).thenReturn("No puede haber penales si no hubo tiempo extra. Debe ingresar los datos de la ronda de penales");

        var actual = controller.methodArgumentNotValidExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void methodArgumentNotValidExceptionHandler_globalErrorAndFieldError() {
        var exception = mock(MethodArgumentNotValidException.class);
        var globalError = mock(ObjectError.class);
        var fieldError = mock(FieldError.class);
        var errorGlobalError = new ErrorDTO.FieldError();
        errorGlobalError.setField("matchUpdateResultDTO");
        errorGlobalError.setMessage("No puede haber penales si no hubo tiempo extra. Debe ingresar los datos de la ronda de penales");
        var errorFieldError = new ErrorDTO.FieldError();
        errorFieldError.setField("id");
        errorFieldError.setMessage("El id del partido no puede ser negativo");
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setErrors(List.of(errorGlobalError, errorFieldError));
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        when(exception.getGlobalErrorCount()).thenReturn(1);
        when(exception.getGlobalErrors()).thenReturn(List.of(globalError));
        when(globalError.getObjectName()).thenReturn("matchUpdateResultDTO");
        when(globalError.getDefaultMessage()).thenReturn("No puede haber penales si no hubo tiempo extra. Debe ingresar los datos de la ronda de penales");
        when(exception.getFieldErrorCount()).thenReturn(1);
        when(exception.getFieldErrors()).thenReturn(List.of(fieldError));
        when(fieldError.getField()).thenReturn("id");
        when(fieldError.getDefaultMessage()).thenReturn("El id del partido no puede ser negativo");

        var actual = controller.methodArgumentNotValidExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void invalidFormatExceptionHandler() {
        var exception = mock(InvalidFormatException.class);
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setError("Error al deserializar '2022-11-21 13:00' a la clase 'LocalDateTime'");
        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        when(exception.getValue()).thenReturn("2022-11-21 13:00");
        doReturn(LocalDateTime.class).when(exception).getTargetType();

        var actual = controller.invalidFormatExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void httpMediaTypeNotSupportedExceptionHandler() {
        var exception = mock(HttpMediaTypeNotSupportedException.class);
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        error.setError("Content-Type 'application/json' no soportado. Esperado: '[application/merge-patch+json]'");
        var expected = ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);

        when(exception.getContentType()).thenReturn(MediaType.APPLICATION_JSON);
        when(exception.getSupportedMediaTypes()).thenReturn(MediaType.parseMediaTypes("application/merge-patch+json"));

        var actual = controller.httpMediaTypeNotSupportedExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void noHandlerFoundExceptionHandler() {
        var exception = mock(NoHandlerFoundException.class);
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setError("No se encontró el recurso '/api/v1/matchesw'");
        var expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

        when(exception.getRequestURL()).thenReturn("/api/v1/matchesw");

        var actual = controller.noHandlerFoundExceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void accessDeniedExceptionHandler() {
        var expected = mock(AccessDeniedException.class);

        var actual = assertThrows(AccessDeniedException.class, () -> controller.accessDeniedExceptionHandler(expected));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void exceptionHandler() {
        var exception = new NullPointerException("NullPointerException");
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setError("NullPointerException");
        var expected = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        var actual = controller.exceptionHandler(exception);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

}