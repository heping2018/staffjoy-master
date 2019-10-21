package com.example.commonlib.commonlib.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DayOfWeekValidator implements ConstraintValidator<DayOfWeek,String> {


    private List<String> dayOfweek = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        if(dayOfweek.contains(value)){
            return true;
        }
        return false;
    }
}
