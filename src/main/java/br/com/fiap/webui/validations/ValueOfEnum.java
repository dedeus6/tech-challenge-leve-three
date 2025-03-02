package br.com.fiap.webui.validations;

import br.com.fiap.webui.validations.validators.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValueOfEnumValidator.class})
public @interface ValueOfEnum {

    Class<? extends  Enum> enumClass();

    String[] exclusions() default {};

    String message() default "Value must be an Enum of {enumClass()}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
