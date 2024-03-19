package com.phung.clothshop.utils.customAnnotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EPronvinceValidator.class)
public @interface EnumValidCheck2 {
    String message() default "Invalid value. Please provide a valid value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}

class EPronvinceValidator implements ConstraintValidator<EnumValidCheck, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValidCheck constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Cho phép giá trị null (có thể tuỳ chỉnh theo yêu cầu)
        }

        try {
            Enum<?>[] enumValues = enumClass.getEnumConstants();
            for (Enum<?> enumValue : enumValues) {
                if (enumValue.name().equalsIgnoreCase(value)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
            // Xử lý ngoại lệ nếu cần
        }

        return false;
    }
}
