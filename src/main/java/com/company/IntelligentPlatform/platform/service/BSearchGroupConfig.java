package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BSearchGroupConfig {

	public String groupInstId();

}
