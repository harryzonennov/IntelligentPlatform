package com.company.IntelligentPlatform.platform.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISEDropDownResourceMapping {
	String resouceMapping() default ServiceEntityStringHelper.EMPTYSTRING;

	String valueFieldName();

	String resourceFile() default ServiceEntityStringHelper.EMPTYSTRING;

}
