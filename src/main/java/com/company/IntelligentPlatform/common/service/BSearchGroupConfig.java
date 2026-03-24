package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BSearchGroupConfig {

	public String groupInstId();

}
