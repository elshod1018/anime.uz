package uz.anime.customAnnotations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import uz.anime.customAnnotations.validators.UniqueUsernameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username is already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
