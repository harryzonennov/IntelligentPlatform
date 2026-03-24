package com.company.IntelligentPlatform.common.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Retention(RetentionPolicy.RUNTIME)
public @interface IServiceModuleFieldConfig {

	String nodeName() default ServiceEntityStringHelper.EMPTYSTRING;
	
	String nodeInstId();

	int docNodeCategory() default DOCNODE_CATE_UNKNOWN;

	
	/**
	 * True means update will be block when calling update framework.
	 */
	boolean blockUpdate() default false;

	int DOCNODE_CATE_UNKNOWN = 0;

	int DOCNODE_CATE_DOC = 1;

	int DOCNODE_CATE_ATTACHMENT = 5;

	int DOCNODE_CATE_MATITEM = 6;

	int DOCNODE_CATE_PARTY = 7;

	int DOCNODE_CATE_ACTNODE = 8;

	int DOCNODE_CATE_DOCFLOW = 9;

}
