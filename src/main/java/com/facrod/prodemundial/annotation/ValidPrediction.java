package com.facrod.prodemundial.annotation;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPrediction.ValidPredictionValidator.class)
public @interface ValidPrediction {

    String message() default "";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    final class ValidPredictionValidator implements ConstraintValidator<ValidPrediction, MatchPredictionCreateDTO> {
        @Override
        public boolean isValid(MatchPredictionCreateDTO prediction, ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();
            if (prediction.getExtraTime() != null) {
                var fullTimeDraw = prediction.getHomeScore() == prediction.getAwayScore();
                if (prediction.getPenalties() != null) {
                    var extraTimeDraw = prediction.getExtraTime().getHomeScore() == prediction.getExtraTime().getAwayScore();
                    context.buildConstraintViolationWithTemplate("Si ingresa penales, el partido y el tiempo extra deben terminar en empate.").addConstraintViolation();
                    return fullTimeDraw && extraTimeDraw;
                }
                context.buildConstraintViolationWithTemplate("Si ingresa tiempo extra, el partido debe terminar en empate.").addConstraintViolation();
                return fullTimeDraw;
            }
            return true;
        }
    }

}
