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
public class PurchaseRequestSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected PurchaseRequestManager purchaseRequestManager;

	@Autowired
	protected PurchaseRequestMaterialItemServiceUIModelExtension purchaseRequestMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return PurchaseRequestSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return PurchaseRequestMaterialItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return purchaseRequestManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return purchaseRequestManager.initStatus(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseRequest.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseRequestMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseRequestActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseRequestActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(PurchaseRequestActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseRequestActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseRequestActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(PurchaseRequestActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(PurchaseRequestActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS)
				.nodeInstCode(PurchaseRequestActionNode.DOC_ACTION_INPROCESS).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(PurchaseRequestParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(PurchaseRequestParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(PurchaseRequestParty.ROLE_PARTYB).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(PurchaseRequestParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(PurchaseRequestParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(PurchaseRequestParty.ROLE_PARTYA).build());
		return searchConfigureTemplateNodeList;
	}

}
