package com.facrod.prodemundial.annotation;

import com.facrod.prodemundial.dto.SignUpDTO;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchingPasswords.MatchingPasswordsValidator.class)
public @interface MatchingPasswords {

    String message() default "Las contraseñas no coinciden";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    final class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, SignUpDTO> {
        @Override
        public boolean isValid(SignUpDTO signUpDTO, ConstraintValidatorContext constraintValidatorContext) {
            return signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword());
        }
    }

}
