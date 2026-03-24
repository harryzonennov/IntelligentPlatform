package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;


@FunctionalInterface
public interface IConvertSearchDataMethod{
		List<?> apply(List<ServiceEntityNode> rawDataList)
				throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceEntityInstallationException, DocActionException;
}
