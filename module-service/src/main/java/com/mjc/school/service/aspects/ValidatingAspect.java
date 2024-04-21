package com.mjc.school.service.aspects;

import com.mjc.school.service.validation.Validator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidatingAspect {
    private final Validator validator;

    @Autowired
    public ValidatingAspect(Validator validator) {
        this.validator = validator;
    }

//    @Before("@annotation(com.mjc.school.service.annotation.ValidatingNews) && args(newsDtoRequest)")
//    public void validateNewsDtoRequest(NewsDtoRequest newsDtoRequest) {
//        validator.validateNewsDtoRequest(newsDtoRequest);
//    }

    @Before("@annotation(com.mjc.school.service.annotation.ValidatingNewsId) && args(id)")
    public void validateNewsDtoId(Long id) {
        validator.validateNewsId(id);
    }
}