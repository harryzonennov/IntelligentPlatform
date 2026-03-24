package com.company.IntelligentPlatform.common.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SEUIModelReferenceMapping {
	String refNodeName();

	String refSEName();

	String fieldName();
}
