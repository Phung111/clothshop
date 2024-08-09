package com.phung.clothshop.utils.customAnnotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.reflect.Method;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumNameValidator.class)
public @interface EnumValidCheckName {
    String message() default "Invalid value. Please provide a valid value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}

class EnumNameValidator implements ConstraintValidator<EnumValidCheckName, CharSequence> {

    private EnumValidCheckName annotation;

    @Override
    public void initialize(EnumValidCheckName constraintAnnotation) {
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
                Method getNameMethod = enumValue.getClass().getMethod("getName");
                String name = (String) getNameMethod.invoke(enumValue);
                if (name.equals(value.toString())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
