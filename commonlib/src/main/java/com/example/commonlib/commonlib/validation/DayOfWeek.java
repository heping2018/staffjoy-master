package com.example.commonlib.commonlib.validation;


import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = DayOfWeekValidator.class)
public @interface DayOfWeek {
    String message() default "unknown day of week";
    Class[] groups() default {};

}
