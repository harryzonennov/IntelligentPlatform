package com.company.IntelligentPlatform.common.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISEDropDownResourceMapping {
	String resouceMapping() default ServiceEntityStringHelper.EMPTYSTRING;

	String valueFieldName();

	String resourceFile() default ServiceEntityStringHelper.EMPTYSTRING;

}
