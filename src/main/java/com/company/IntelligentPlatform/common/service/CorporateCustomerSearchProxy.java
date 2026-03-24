package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateCustomerSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class CorporateCustomerSearchProxy extends ServiceSearchProxy {
	
	@Autowired
	protected CorporateCustomerSearchHelpProxy corporateCustomerSearchHelpProxy;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return CorporateCustomerSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return corporateCustomerManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return null;
	}

	@Override
	public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
			ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
			LogonInfoException {
		List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
		searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
		searchContext.setFuzzyFlag(true);
		CorporateCustomerSearchModel corporateCustomerSearchModel = (CorporateCustomerSearchModel) searchContext.getSearchModel();
		corporateCustomerSearchModel.setCustomerType(CorporateCustomer.CUSTOMERTYPE_STANDARD);
		return bsearchService.doSearchWithContext(
				searchContext, searchNodeConfigList);
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// start node:[Corporate customer root node]
		List<BSearchNodeComConfigure> searchNodeConfigList =
				SearchModelConfigHelper.buildParentChildConfigure(CorporateCustomer.class,
						CorporateContactPerson.class);
		// search node [Corporate contact person->Individual Customer]
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(IndividualCustomer.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(CorporateContactPerson.NODENAME).build());
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				CorporateCustomerActionNode.class, CorporateCustomerActionNode.NODEINST_ACTION_SUBMIT,
				CorporateCustomerActionNode.DOC_ACTION_SUBMIT,
						null, CorporateCustomer.SENAME
				));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				CorporateCustomerActionNode.class, CorporateCustomerActionNode.NODEINST_ACTION_APPROVE,
				CorporateCustomerActionNode.DOC_ACTION_APPROVE,
				null, CorporateCustomer.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				CorporateCustomerActionNode.class, CorporateCustomerActionNode.NODEINST_ACTION_ACTIVE,
				CorporateCustomerActionNode.DOC_ACTION_ACTIVE,
				null, CorporateCustomer.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				CorporateCustomerActionNode.class, CorporateCustomerActionNode.NODEINST_ACTION_REINIT,
				CorporateCustomerActionNode.DOC_ACTION_REINIT,
				null, CorporateCustomer.SENAME
		));
		searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
				CorporateCustomerActionNode.class, CorporateCustomerActionNode.NODEINST_ACTION_ARCHIVE,
				CorporateCustomerActionNode.DOC_ACTION_ARCHIVE,
				null, CorporateCustomer.SENAME
		));
		searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(CorporateCustomer.class, null));
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		// search node [Corporate contact person->Individual Customer]
		List<BSearchNodeComConfigure> searchNodeConfigList = SearchModelConfigHelper.buildChildParentConfigure(CorporateContactPerson.class,
				CorporateCustomer.class);
		searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(IndividualCustomer.class).
				toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(CorporateContactPerson.NODENAME).build());
		return searchNodeConfigList;
	}

}
