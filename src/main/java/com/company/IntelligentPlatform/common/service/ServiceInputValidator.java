package com.company.IntelligentPlatform.common.service;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceInputValidator {

    @Pointcut("execution(* com.company.IntelligentPlatform.common.service.Common..*.*(..))")
    public void anyServerMethod() {

    }

    @Before("anyServerMethod()")
    public void before() {
        System.out.println("before any method");
    }

}
