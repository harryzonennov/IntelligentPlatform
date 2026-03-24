package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.sales.dto.SalesReturnMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.sales.dto.SalesReturnMaterialItemSearchModel;
import com.company.IntelligentPlatform.sales.dto.SalesReturnOrderSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class SalesReturnOrderSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected SalesReturnOrderManager salesReturnOrderManager;

	@Autowired
	protected SalesReturnMaterialItemServiceUIModelExtension salesReturnMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return SalesReturnOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return SalesReturnMaterialItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return salesReturnOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return salesReturnOrderManager.initStatus(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesReturnOrder.class).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesReturnMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesReturnOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesReturnOrderActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(SalesReturnOrderActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesReturnOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesReturnOrderActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(SalesReturnOrderActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesReturnOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesReturnOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(SalesReturnOrderActionNode.DOC_ACTION_DELIVERY_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(SalesReturnOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesReturnOrderParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(SalesReturnOrderParty.ROLE_SOLD_TO_PARTY).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(SalesReturnOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesReturnOrderParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(SalesReturnOrderParty.ROLE_SOLD_FROM_PARTY).build());
		return searchConfigureTemplateNodeList;
	}

}