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
public class OutboundDeliverySearchProxy extends ServiceSearchProxy {

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected OutboundItemServiceUIModelExtension outboundItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return OutboundDeliverySearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return OutboundItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return outboundDeliveryManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return outboundDeliveryManager.initStatusMap(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(OutboundDelivery.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(OutboundItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(OutboundDeliveryActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(OutboundDeliveryActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(OutboundDeliveryActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(OutboundDeliveryActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(OutboundDeliveryActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(OutboundDeliveryActionNode.DOC_ACTION_RECORD_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(OutboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(OutboundDeliveryParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(OutboundDeliveryParty.PARTY_ROLE_CUSTOMER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(OutboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(OutboundDeliveryParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(OutboundDeliveryParty.PARTY_ROLE_SALESORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(OutboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(OutboundDeliveryParty.PARTY_NODEINST_PUR_SUPPLIER).nodeInstCode(OutboundDeliveryParty.PARTY_ROLE_SUPPLIER).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(OutboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(OutboundDeliveryParty.PARTY_NODEINST_PUR_ORG).nodeInstCode(OutboundDeliveryParty.PARTY_ROLE_PURORG).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(OutboundDeliveryParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(OutboundDeliveryParty.PARTY_NODEINST_PROD_ORG).nodeInstCode(OutboundDeliveryParty.PARTY_ROLE_PRODORG).build());
		addDefaultDeliveryDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InboundItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		return searchConfigureTemplateNodeList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicSearchNodeConfigureList(searchContext);
		// start node:[root]
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, OutboundDelivery.SENAME);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicItemSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, OutboundDelivery.SENAME);
		return searchNodeConfigList;
	}

}
