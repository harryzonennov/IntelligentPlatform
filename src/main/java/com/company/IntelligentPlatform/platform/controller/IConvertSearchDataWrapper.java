package com.company.IntelligentPlatform.platform.controller;

import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.SearchContext;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

import java.util.List;

@FunctionalInterface
public interface IConvertSearchDataWrapper {
		List apply(SearchContext searchContext)
				throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException;
}
