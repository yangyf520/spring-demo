package com.example.demo.controller;

import com.example.demo.service.ValidFormService;
import com.example.demo.vo.PersonForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;

@Controller
public class FormController extends WebMvcConfigurerAdapter {

    @Autowired
    private ValidFormService validFormService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/valid")
    public String showForm(PersonForm personForm, BindingResult bindingResult) {
        return validFormService.validForm(personForm, bindingResult);
    }

    @PostMapping("/check")
    public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("FORM校验message：");
            Field[] fields = personForm.getClass().getDeclaredFields();
            for (Field field : fields) {
                List<FieldError> fieldErrorList = bindingResult.getFieldErrors(field.getName());
                if (fieldErrorList != null && fieldErrorList.size() > 0) {
                    FieldError fieldError = fieldErrorList.get(0);
                    System.out.println(fieldError.getField() + fieldError.getDefaultMessage());
                }

            }

            return "form";
        }

        return "redirect:/results";
    }
}