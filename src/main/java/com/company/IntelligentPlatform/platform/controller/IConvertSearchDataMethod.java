package com.company.IntelligentPlatform.platform.controller;

import java.util.List;

import com.company.IntelligentPlatform.platform.service.DocActionException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@FunctionalInterface
public interface IConvertSearchDataMethod{
		List<?> apply(List<ServiceEntityNode> rawDataList)
				throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException, DocActionException;
}
