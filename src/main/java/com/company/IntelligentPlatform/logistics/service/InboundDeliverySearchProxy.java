package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.dto.InboundItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.dto.InboundDeliverySearchModel;
import com.company.IntelligentPlatform.logistics.dto.InboundItemSearchModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class InboundDeliverySearchProxy extends ServiceSearchProxy{

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected InboundItemServiceUIModelExtension inboundItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return InboundDeliverySearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return InboundItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return inboundDeliveryManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return inboundDeliveryManager.initStatusMap(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundDelivery.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundDeliveryActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InboundDeliveryActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(InboundDeliveryActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundDeliveryActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(InboundDeliveryActionNode.DOC_ACTION_RECORD_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(InboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(InboundDeliveryParty.PARTY_ROLE_CUSTOMER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InboundDeliveryParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(InboundDeliveryParty.PARTY_ROLE_SALESORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(InboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(InboundDeliveryParty.PARTY_ROLE_SUPPLIER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InboundDeliveryParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(InboundDeliveryParty.PARTY_ROLE_PURORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(InboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(InboundDeliveryParty.PARTY_NODEINST_PROD_ORG).nodeInstCode(InboundDeliveryParty.PARTY_ROLE_PRODORG).build());
		addDefaultDeliveryDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		return searchConfigureTemplateNodeList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicItemSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InboundDelivery.SENAME);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicSearchNodeConfigureList(searchContext);
		// Search node:[InboundDelivery]
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InboundDelivery.SENAME);
		return searchNodeConfigList;
	}

}
