package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.model.*;
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
public class PurchaseReturnOrderSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected PurchaseReturnOrderManager purchaseReturnOrderManager;

	@Autowired
	protected PurchaseReturnMaterialItemServiceUIModelExtension purchaseReturnMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return PurchaseReturnOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return PurchaseReturnMaterialItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return purchaseReturnOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return purchaseReturnOrderManager.initStatus(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseReturnOrder.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseReturnMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseReturnOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseReturnOrderActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(PurchaseReturnOrderActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseReturnOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseReturnOrderActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(PurchaseReturnOrderActionNode.DOC_ACTION_APPROVE).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(PurchaseReturnOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(PurchaseReturnOrderParty.ROLE_PARTYA).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(PurchaseReturnOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(PurchaseReturnOrderParty.ROLE_PARTYB).build());
		return searchConfigureTemplateNodeList;
	}


}
