package com.company.IntelligentPlatform.sales.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.sales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.sales.dto.SalesContractMaterialItemSearchModel;
import com.company.IntelligentPlatform.sales.dto.SalesContractSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;

@Service
public class SalesContractSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected SalesContractManager salesContractManager;

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected SalesContractMaterialItemServiceUIModelExtension salesContractMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return SalesContractSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return salesContractManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return salesContractManager.initStatus(languageCode);
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return SalesContractMaterialItemSearchModel.class;
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContract.class).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesContractActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(SalesContractActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesContractActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(SalesContractActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesContractActionNode.NODEINST_ACTION_INPLAN)
				.nodeInstCode(SalesContractActionNode.DOC_ACTION_INPLAN).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(SalesContractActionNode.DOC_ACTION_DELIVERY_DONE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(SalesContractActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(SalesContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(SalesContractActionNode.DOC_ACTION_DELIVERY_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(SalesContractParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesContractParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(SalesContractParty.ROLE_SOLD_TO_PARTY).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(SalesContractParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(SalesContractParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(SalesContractParty.ROLE_SOLD_FROM_PARTY).build());
		return searchConfigureTemplateNodeList;
	}


}