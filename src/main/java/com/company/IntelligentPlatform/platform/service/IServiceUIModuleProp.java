package com.company.IntelligentPlatform.platform.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IServiceUIModuleProp {

    int documentType();

}
