package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceInputValidator {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInputValidator.class);

    @Pointcut("execution(* com.company.IntelligentPlatform.common.service.Common..*.*(..))")
    public void anyServerMethod() {

    }

    @Before("anyServerMethod()")
    public void before() {
        logger.debug("before any method");
    }

}
