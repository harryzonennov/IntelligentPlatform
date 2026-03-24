package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.LogonUserOrgSearchModel;
import com.company.IntelligentPlatform.common.dto.OrganizationSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class OrganizationSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected OrganizationManager organizationManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return OrganizationSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return LogonUserOrgSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return organizationManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[Organization root node->LogonUserOrgReference]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(Organization.class,
						LogonUserOrgReference.class);
		// search node [LogonUserOrgReference->LogonUser]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(LogonUser.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(LogonUserOrgReference.NODENAME).build());
		// search node [Organization->Parent Organization]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Organization.class).nodeInstId(OrganizationSearchModel.NODEINST_ID_PARENTORG).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("parentOrganizationUUID").baseNodeInstId(Organization.SENAME).build());
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// search node [Corporate contact person->Individual Customer]
		List<BSearchNodeComConfigure> searchNodeConfigList = SearchModelConfigHelper.buildChildParentConfigure(LogonUserOrgReference.class,
				Organization.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(IndividualCustomer.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(LogonUserOrgReference.NODENAME).build());
		return searchNodeConfigList;
	}

}
