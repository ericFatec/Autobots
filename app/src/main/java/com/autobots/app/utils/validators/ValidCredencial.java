package com.autobots.app.utils.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CredencialVerificador.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCredencial {
    String message() default "Informe login e senha, ou apenas o código.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
