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
@Constraint(validatedBy = ValidPenaltiesRound.ValidPenaltiesRoundValidator.class)
public @interface ValidPenaltiesRound {

    String message() default "No puede haber penales si no hubo tiempo extra. Debe ingresar los datos de la ronda de penales";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    final class ValidPenaltiesRoundValidator implements ConstraintValidator<ValidPenaltiesRound, MatchUpdateResultDTO> {
        @Override
        public boolean isValid(MatchUpdateResultDTO match, ConstraintValidatorContext constraintValidatorContext) {
            if (match.getPenalties() == null || Boolean.FALSE.equals(match.getPenalties())) return true;
            return Boolean.TRUE.equals(match.getExtraTime()) && match.getPenaltiesRound() != null;
        }
    }

}
