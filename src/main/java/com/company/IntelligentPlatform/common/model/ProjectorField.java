package com.company.IntelligentPlatform.common.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectorField {
	String projSEName();

	String projPackageName();

	String projNodeName();
}
