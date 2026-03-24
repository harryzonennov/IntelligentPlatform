package com.company.IntelligentPlatform.common.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ISystemExecutorAttr {
	
	public static int STATUS_INIT = 1;
	
	public static int STATUS_ACTIVE = 2;
	
	public static int STATUS_ARCHIVE = 3;
	
	public static final int EXECUTIONTYPE_SYSTEMINSTALL = 1;
	
	public static final int EXECUTIONTYPE_SYSTEMUPGRADE = 2;
	
	public static final int EXECUTIONTYPE_INITIALIZE = 3;
	
	public static final int EXECUTIONTYPE_ADMINTOOL = 4;
	
	int executionType() default EXECUTIONTYPE_SYSTEMINSTALL;
	
	int status() default STATUS_INIT;

}
