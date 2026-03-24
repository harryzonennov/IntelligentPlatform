package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchContext;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;


@FunctionalInterface
public interface IConvertSearchDataWrapper {
		List apply(SearchContext searchContext)
				throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException;
}
