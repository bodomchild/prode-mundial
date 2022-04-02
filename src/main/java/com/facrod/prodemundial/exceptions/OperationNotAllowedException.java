package com.facrod.prodemundial.exceptions;

import org.springframework.http.HttpStatus;

public class OperationNotAllowedException extends AppException {

    public OperationNotAllowedException() {
        super(HttpStatus.FORBIDDEN, "Operation not allowed");
    }

}
