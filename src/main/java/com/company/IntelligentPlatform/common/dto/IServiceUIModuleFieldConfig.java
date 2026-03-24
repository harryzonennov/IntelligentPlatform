package com.company.IntelligentPlatform.common.dto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface IServiceUIModuleFieldConfig {

	String nodeName();
	
	String nodeInstId();
	
	/**
	 * Name of Convert service entity node To UI module Method defined in manager
	 * @return
	 */
	String convToUIMethod()  default ServiceEntityStringHelper.EMPTYSTRING;
	
	/**
	 * Name of Convert UI module To service entity Method defined in manager
	 * @return
	 */
	String convUIToMethod()  default ServiceEntityStringHelper.EMPTYSTRING;

}
