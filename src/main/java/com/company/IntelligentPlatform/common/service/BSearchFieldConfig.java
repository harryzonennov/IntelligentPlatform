package com.company.IntelligentPlatform.common.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface BSearchFieldConfig {

	int FIELDTYPE_EQ = 1;

	int FIELDTYPE_LOW = 2;

	int FIELDTYPE_HIGH = 3;

	String seName() default ServiceEntityStringHelper.EMPTYSTRING;

	String nodeName() default ServiceEntityStringHelper.EMPTYSTRING;

	String nodeInstID()  default ServiceEntityStringHelper.EMPTYSTRING;

	String fieldName();

	/**
	 * Indicate the sub node inst Id inside group
	 */
    String subNodeInstId() default ServiceEntityStringHelper.EMPTYSTRING;

	int fieldType() default FIELDTYPE_EQ;

	boolean showOnUI() default true;

}
