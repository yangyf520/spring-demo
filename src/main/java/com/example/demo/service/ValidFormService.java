package com.example.demo.service;

import com.example.demo.vo.PersonForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class ValidFormService {

    private final static Logger LOG = LoggerFactory.getLogger(ValidFormService.class);

    private static Validator validator;

    public String validForm(PersonForm personForm, BindingResult bindingResult){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator =  factory.getValidator();
        Set<ConstraintViolation<PersonForm>> violations = validator.validate(personForm);
        for(ConstraintViolation<PersonForm> violation:violations){
            LOG.debug(violation.getMessage());
        }

        return null;
    }
}
