package com.example.commonlib.commonlib.validation;


import javax.validation.Constraint;
import java.beans.Transient;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidation.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Invalid phone number";
    Class[] groups() default{};
    Class[] payLoad() default {};
}
