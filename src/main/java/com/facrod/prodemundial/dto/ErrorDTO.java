package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorDTO {

    private String status;
    private String error;
    private List<FieldError> errors;

    @Getter
    @Setter
    public static class FieldError {
        private String field;
        private String message;

        public FieldError() {
        }
    }

}
