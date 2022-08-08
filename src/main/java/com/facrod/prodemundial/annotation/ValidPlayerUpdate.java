package com.facrod.prodemundial.annotation;

import com.facrod.prodemundial.dto.PlayerUpdateDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPlayerUpdate.ValidPlayerUpdateValidator.class)
public @interface ValidPlayerUpdate {

    String message() default "Debe ingresar alguno de los siguientes campos: goals, yellow_cards, red_cards";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    final class ValidPlayerUpdateValidator implements ConstraintValidator<ValidPlayerUpdate, PlayerUpdateDTO> {
        @Override
        public boolean isValid(com.facrod.prodemundial.dto.PlayerUpdateDTO player, ConstraintValidatorContext constraintValidatorContext) {
            return player.getGoals() != null || player.getYellowCards() != null || player.getRedCards() != null;
        }
    }

}
