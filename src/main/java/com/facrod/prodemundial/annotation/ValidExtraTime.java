package com.facrod.prodemundial.annotation;

import com.facrod.prodemundial.dto.MatchUpdateResultDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidExtraTime.ValidExtraTimeValidator.class)
public @interface ValidExtraTime {

    String message() default "Debe ingresar la cantidad de goles de ambos equipos en el tiempo extra";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    final class ValidExtraTimeValidator implements ConstraintValidator<ValidExtraTime, MatchUpdateResultDTO> {
        @Override
        public boolean isValid(MatchUpdateResultDTO match, ConstraintValidatorContext constraintValidatorContext) {
            if (match.getExtraTime() == null || Boolean.FALSE.equals(match.getExtraTime())) return true;
            return match.getExtraTimeHomeScore() != null && match.getExtraTimeAwayScore() != null;
        }
    }

}
