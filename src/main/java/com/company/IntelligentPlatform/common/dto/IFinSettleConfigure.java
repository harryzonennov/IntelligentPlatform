package com.company.IntelligentPlatform.common.dto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface IFinSettleConfigure {
	
	int FIELDTYPE_ALLAMOUNT = 1;

	int FIELDTYPE_RECORDED = 2;

	int FIELDTYPE_TORECORD = 3;
	
	int FIELDTYPE_FINACCOBJ = 4;
	
	int fieldType();
	
	String coreSettleID() default ServiceEntityStringHelper.EMPTYSTRING;

}
