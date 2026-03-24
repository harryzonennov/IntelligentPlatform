package com.company.IntelligentPlatform.common.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.*;


@Retention(RetentionPolicy.RUNTIME)
public @interface ISQLSepcifyAttribute {
	
	int FIELDLENGTH_SHORT = 20;
	
	int FIELDLENGTH_MID = 100;
	
	int FIELDLENGTH_LONG = 1600;
	
	int FIELDLENGTH_200 = 200;
	
	int FIELDLENGTH_300 = 300;
	
	int FIELDLENGTH_800 = 800;
	
	int FIELDLENGTH_2000 = 2000;
	
	int FIELDLENGTH_3000 = 3000;
	
	int FIELDLENGTH_8000 = 8000;
	
	int FIELDLENGTH_20000 = 20000;

	String SUBTYPE_TEXT = "text";

	String SUBTYPE_JSON = "json";
	
	int fieldLength() default FIELDLENGTH_MID;

	String subType() default ServiceEntityStringHelper.EMPTYSTRING;

}
