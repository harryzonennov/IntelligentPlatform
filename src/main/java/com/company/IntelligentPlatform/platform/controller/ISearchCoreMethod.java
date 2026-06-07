package com.company.IntelligentPlatform.platform.controller;

import java.util.List;

import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.controller.SEUIComModel;

@FunctionalInterface
public interface ISearchCoreMethod<T extends SEUIComModel>{
		 List<ServiceEntityNode> apply(T searchModel)
				 throws SearchConfigureException, LogonInfoException,
				 ServiceEntityInstallationException, ServiceEntityConfigureException;
}
