package com.company.IntelligentPlatform.common.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISearchFieldConfig {

	public static final int FIELDTYPE_EQ = 1;

	public static final int FIELDTYPE_LOW = 2;

	public static final int FIELDTYPE_HIGH = 3;

	String seName() default ServiceEntityStringHelper.EMPTYSTRING;

	String nodeName() default ServiceEntityStringHelper.EMPTYSTRING;

	String nodeInstID() default ServiceEntityStringHelper.EMPTYSTRING;

	String fieldName();

	int fieldType() default FIELDTYPE_EQ;

	/**
	 * [extralID] is used for the case to indicate different SE instance in
	 * dynamic search
	 * 
	 * @return
	 */
	String extralID() default ServiceEntityStringHelper.EMPTYSTRING;

}
