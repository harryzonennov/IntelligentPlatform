package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.BSearchResponse;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContext;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@FunctionalInterface
public interface ISearchDataMethod {
	BSearchResponse apply(SearchContext searchContext)
			throws SearchConfigureException, ServiceEntityInstallationException,
			AuthorizationException, LogonInfoException, NodeNotFoundException, ServiceEntityConfigureException;
}


