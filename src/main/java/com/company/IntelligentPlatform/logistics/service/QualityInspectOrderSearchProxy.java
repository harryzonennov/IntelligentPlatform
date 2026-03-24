package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@Service
public class QualityInspectOrderSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected QualityInspectMatItemServiceUIModelExtension qualityInspectMatItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return QualityInspectOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return QualityInspectMatItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return qualityInspectOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return qualityInspectOrderManager.initStatusMap(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInspectOrder.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInspectMatItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInspectPropertyItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_UNKNOWN).baseNodeInstId(QualityInspectMatItem.NODENAME).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInsOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(QualityInsOrderActionNode.NODEINST_ACTION_STRATTEST)
				.nodeInstCode(QualityInsOrderActionNode.DOC_ACTION_START_TEST).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInsOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(QualityInsOrderActionNode.NODEINST_ACTION_TEST_DONE)
				.nodeInstCode(QualityInsOrderActionNode.DOC_ACTION_TESTDONE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInsOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(QualityInsOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(QualityInsOrderActionNode.DOC_ACTION_DELIVERY_DONE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(QualityInsOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(QualityInsOrderActionNode.NODEINST_ACTION_PROCESS_DONE)
				.nodeInstCode(QualityInsOrderActionNode.DOC_ACTION_PROCESS_DONE).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(QualityInspectOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(QualityInspectOrderParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(QualityInspectOrderParty.PARTY_ROLE_CUSTOMER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(QualityInspectOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(QualityInspectOrderParty.PARTY_NODEINST_PROD_ORG).nodeInstCode(QualityInspectOrderParty.PARTY_ROLE_PRODORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(QualityInspectOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(QualityInspectOrderParty.PARTY_ROLE_PURORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(QualityInspectOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(QualityInspectOrderParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(QualityInspectOrderParty.PARTY_ROLE_SALESORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(QualityInspectOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(QualityInspectOrderParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(QualityInspectOrderParty.PARTY_ROLE_SUPPLIER).build());
		return searchConfigureTemplateNodeList;
	}

}
