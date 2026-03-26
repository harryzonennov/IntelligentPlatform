package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.WasteProcessMaterialItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.WasteProcessMaterialItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.dto.WasteProcessOrderSearchModel;
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
public class WasteProcessOrderSearchProxy extends ServiceSearchProxy {

	@Autowired
	protected WasteProcessOrderManager wasteProcessOrderManager;

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected WasteProcessMaterialItemServiceUIModelExtension wasteProcessMaterialItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return WasteProcessOrderSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return wasteProcessOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return wasteProcessOrderManager.initStatus(languageCode);
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return WasteProcessMaterialItemSearchModel.class;
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WasteProcessOrder.class).build());
		addDefaultDocFlowConfigureList(searchConfigureTemplateNodeList);
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WasteProcessMaterialItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WasteProcessOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(WasteProcessOrderActionNode.NODEINST_ACTION_SUBMIT)
				.nodeInstCode(WasteProcessOrderActionNode.DOC_ACTION_SUBMIT).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WasteProcessOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(WasteProcessOrderActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(WasteProcessOrderActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(WasteProcessOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(WasteProcessOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(WasteProcessOrderActionNode.DOC_ACTION_DELIVERY_DONE).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(CorporateCustomer.class).
				targetContactClass(IndividualCustomer.class)
				.nodeClass(WasteProcessOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WasteProcessOrderParty.PARTY_NODEINST_SOLD_CUSTOMER).nodeInstCode(WasteProcessOrderParty.ROLE_SOLD_TO_PARTY).build());
		searchConfigureTemplateNodeList.add(new InvolvePartySearchConfigTemplateBuilder().targetPartyClass(Organization.class).
				targetContactClass(Employee.class)
				.nodeClass(WasteProcessOrderParty.class).nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
				.nodeInstId(WasteProcessOrderParty.PARTY_NODEINST_SOLD_ORG).nodeInstCode(WasteProcessOrderParty.ROLE_SOLD_FROM_PARTY).build());
		return searchConfigureTemplateNodeList;
	}

}
