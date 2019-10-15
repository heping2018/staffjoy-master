package com.example.commonlib.commonlib.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumber,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        return value != null && value.matches("[0-9]+") &&
                (value.length() >8) && (value.length() <14);
    }
}
