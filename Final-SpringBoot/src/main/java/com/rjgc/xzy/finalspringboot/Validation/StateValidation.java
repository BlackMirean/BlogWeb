package com.rjgc.xzy.finalspringboot.Validation;

import com.rjgc.xzy.finalspringboot.Anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }

        if (s.equals("草稿") || s.equals("已发布")) {
            return true;
        }
        return false;
    }
}
