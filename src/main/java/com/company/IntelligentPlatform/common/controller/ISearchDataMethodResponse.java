package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.dto.DataTableRequestData;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.BSearchResponse;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;


@FunctionalInterface
public interface ISearchDataMethodResponse {
	BSearchResponse apply(DataTableRequestData dataTableRequestData) throws SearchConfigureException,
            ServiceEntityInstallationException, AuthorizationException, LogonInfoException;
}


