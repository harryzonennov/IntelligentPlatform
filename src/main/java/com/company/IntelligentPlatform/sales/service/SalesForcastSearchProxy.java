package com.company.IntelligentPlatform.sales.service;

import com.company.IntelligentPlatform.sales.dto.SalesForcastMaterialItemSearchModel;
import com.company.IntelligentPlatform.sales.dto.SalesForcastMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.*;
import com.company.IntelligentPlatform.sales.dto.SalesForcastSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SalesForcastSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected SalesForcastManager salesForcastManager;

	@Autowired
	protected SalesForcastMaterialItemServiceUIModelExtension salesForcastMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return SalesForcastSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return SalesForcastMaterialItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return salesForcastManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return salesForcastManager.initStatus(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesForcast.class).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesForcastMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesForcastActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesForcastActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(SalesForcastActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesForcastActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesForcastActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(SalesForcastActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(SalesForcastParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesForcastParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(SalesForcastParty.ROLE_SOLD_TO_PARTY).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(SalesForcastParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesForcastParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(SalesForcastParty.ROLE_SOLD_FROM_PARTY).build());
		return searchConfigureTemplateNodeList;
	}

}