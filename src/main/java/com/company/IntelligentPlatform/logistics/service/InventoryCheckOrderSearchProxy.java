package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.dto.InventoryCheckOrderSearchModel;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckItemSearchModel;
import com.company.IntelligentPlatform.logistics.dto.InventoryCheckItemServiceUIModelExtension;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventoryCheckOrderSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected InventoryCheckOrderManager inventoryCheckOrderManager;

	@Autowired
	protected InventoryCheckItemServiceUIModelExtension inventoryCheckItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return InventoryCheckOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return InventoryCheckItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return inventoryCheckOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return inventoryCheckOrderManager.initStatusMap(languageCode);
	}

	@Override
	public List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> getDocSearchConfigureListTemplate() throws SearchConfigureException {
		List<SearchConfigureTemplateBuilder.SearchConfigureTemplateNode> searchConfigureTemplateNodeList = new ArrayList<>();
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryCheckOrder.class).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryCheckItem.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryCheckOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InventoryCheckOrderActionNode.NODEINST_ACTION_APPROVE)
				.nodeInstCode(InventoryCheckOrderActionNode.DOC_ACTION_APPROVE).build());
		searchConfigureTemplateNodeList.add(new SearchConfigureTemplateBuilder().nodeClass(InventoryCheckOrderActionNode.class).
				nodeCategory(IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE).nodeInstId(InventoryCheckOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
				.nodeInstCode(InventoryCheckOrderActionNode.DOC_ACTION_RECORD_DONE).build());
		return searchConfigureTemplateNodeList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InventoryCheckOrder.SENAME);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = super.getBasicItemSearchNodeConfigureList(searchContext);
		SearchDocConfigHelper.genWarehouseSearchNodeConfigureList(searchNodeConfigList, InboundDelivery.SENAME);
		return searchNodeConfigList;
	}

}
