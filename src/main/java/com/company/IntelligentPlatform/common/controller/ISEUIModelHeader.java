package com.company.IntelligentPlatform.common.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISEUIModelHeader {
	/**
	 * i18nProperty file name
	 * 
	 * @return
	 */
	String i18nFileName() default ServiceEntityStringHelper.EMPTYSTRING;

	/**
	 * Back end SE Node name
	 * 
	 * @return
	 */
	String modelName() default ServiceEntityStringHelper.EMPTYSTRING;

	
}
