package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@FunctionalInterface
public interface ISearchCoreMethod<T extends SEUIComModel>{
		 List<ServiceEntityNode> apply(T searchModel)
				 throws SearchConfigureException, LogonInfoException,
				 ServiceEntityInstallationException, ServiceEntityConfigureException;
}
