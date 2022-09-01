package com.ccat.springtest.validation;

import org.apache.commons.validator.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomEmailValidatorImp implements ConstraintValidator<CustomEmailValidator, String> {

    private int min;

    @Override
    public void initialize(CustomEmailValidator constraintAnnotation) {
        min = constraintAnnotation.min();
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email.length() < min) { //|| !EmailValidator.getInstance().isValid(email)) {
            return false;
        }

        return true;
    }
}
