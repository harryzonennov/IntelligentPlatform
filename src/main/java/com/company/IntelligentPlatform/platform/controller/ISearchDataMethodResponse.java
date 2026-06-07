package com.company.IntelligentPlatform.platform.controller;

import com.company.IntelligentPlatform.platform.dto.DataTableRequestData;
import com.company.IntelligentPlatform.platform.service.AuthorizationException;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.LogonInfoException;
import com.company.IntelligentPlatform.platform.service.BSearchResponse;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

import java.util.List;

@FunctionalInterface
public interface ISearchDataMethodResponse {
	BSearchResponse apply(DataTableRequestData dataTableRequestData) throws SearchConfigureException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException;
}

