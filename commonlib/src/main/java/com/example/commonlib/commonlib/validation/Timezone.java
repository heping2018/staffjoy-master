package com.example.commonlib.commonlib.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = TimezoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Timezone {
     String message() default "Invalid timezone";
     Class[] groups();
}
