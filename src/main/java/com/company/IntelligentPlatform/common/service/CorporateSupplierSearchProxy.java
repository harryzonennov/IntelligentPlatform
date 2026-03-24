package com.company.IntelligentPlatform.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateSupplierSearchModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomerActionNode;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class CorporateSupplierSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Override
	public Class<?> getDocSearchModelCls() {
		return CorporateSupplierSearchModel.class;
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
		CorporateSupplierSearchModel corporateSupplierSearchModel = (CorporateSupplierSearchModel) searchContext.getSearchModel();
		corporateSupplierSearchModel.setCustomerType(CorporateCustomer.CUSTOMERTYPE_SUPPLIER);
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

}
