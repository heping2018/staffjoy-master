package com.example.commonlib.commonlib.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

public class TimezoneValidator implements ConstraintValidator<Timezone,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        return Arrays.asList(TimeZone.getAvailableIDs()).contains(value);
    }
}
