package com.phung.clothshop.utils.customAnnotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValidCheck {
    String message() default "Invalid value. Please provide a valid value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}

class EnumValidator implements ConstraintValidator<EnumValidCheck, CharSequence> {

    private EnumValidCheck annotation;

    @Override
    public void initialize(EnumValidCheck constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Cho phép giá trị null
        }

        Class<? extends Enum<?>> enumClass = this.annotation.enumClass();

        try {
            Enum<?>[] enumValues = (Enum<?>[]) enumClass.getMethod("values").invoke(null);
            for (Enum<?> enumValue : enumValues) {
                if (enumValue.name().equals(value.toString())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
