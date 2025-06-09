package raisetech.StudentManagement.data.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import raisetech.StudentManagement.data.validation.validator.EnumStatusValueValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EnumStatusValueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumStatusValue {
    Class<? extends Enum<?>> enumClass();

    String message() default "無効な値です";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}