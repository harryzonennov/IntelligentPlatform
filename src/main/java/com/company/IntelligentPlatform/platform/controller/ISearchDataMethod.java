package com.company.IntelligentPlatform.platform.controller;

import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.BSearchResponse;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.service.SearchContext;
import com.company.IntelligentPlatform.platform.model.NodeNotFoundException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@FunctionalInterface
public interface ISearchDataMethod {
	BSearchResponse apply(SearchContext searchContext)
			throws SearchConfigureException, ServiceEntityInstallationException,
			AuthorizationException, LogonInfoException, NodeNotFoundException, ServiceEntityConfigureException;
}

