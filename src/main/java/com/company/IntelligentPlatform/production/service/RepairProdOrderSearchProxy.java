package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepairProdOrderSearchProxy extends ServiceSearchProxy{

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected SearchDocConfigHelper searchDocConfigHelper;

	@Autowired
	protected RepairProdTargetMatItemServiceUIModelExtension repairProdTargetMatItemServiceUIModelExtension;

	@Override
	public Class<?> getDocSearchModelCls() {
		return RepairProdOrderSearchModel.class;
	}

	@Override
	public Class<?> getMatItemSearchModelCls() {
		return RepairProdTargetMatItemSearchModel.class;
	}

	@Override
	public String getAuthorizationResource() {
		return repairProdOrderManager.getAuthorizationResource();
	}

	@Override
	public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return repairProdOrderManager.initItemStatusMap(languageCode);
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node:[repairProdOrderItem]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(RepairProdOrderItem.SENAME);
		searchNodeConfig0.setNodeName(RepairProdOrderItem.NODENAME);
		searchNodeConfig0.setNodeInstID(RepairProdOrderItem.NODENAME);
		searchNodeConfig0.setStartNodeFlag(false);
		searchNodeConfig0
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig0.setBaseNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[prodOrderSupplyWarehouse]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(ProdOrderSupplyWarehouse.SENAME);
		searchNodeConfig2.setNodeName(ProdOrderSupplyWarehouse.NODENAME);
		searchNodeConfig2.setNodeInstID(ProdOrderSupplyWarehouse.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);

		// Search node:[repairProdOrder]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(RepairProdOrder.SENAME);
		searchNodeConfig4.setNodeName(RepairProdOrder.NODENAME);
		searchNodeConfig4.setNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfig4.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig4);

		// Search node:[ProdWorkCenter]
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setSeName(ProdWorkCenter.SENAME);
		searchNodeConfig5.setNodeName(ProdWorkCenter.NODENAME);
		searchNodeConfig5.setNodeInstID(ProdWorkCenter.SENAME);
		searchNodeConfig5.setStartNodeFlag(false);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig5.setMapBaseFieldName("refWocUUID");
		searchNodeConfig5.setMapSourceFieldName("uuid");
		searchNodeConfig5.setBaseNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig5);
		// Search node:[itemMaterialSKU]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig6.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig6.setNodeInstID("itemMaterialSKU");
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig6.setBaseNodeInstID(RepairProdOrderItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig6);
		// Search node:[outBillOfMaterialOrder]
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(BillOfMaterialOrder.SENAME);
		searchNodeConfig7.setNodeName(BillOfMaterialOrder.NODENAME);
		searchNodeConfig7.setNodeInstID(BillOfMaterialOrder.SENAME);
		searchNodeConfig7.setStartNodeFlag(false);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig7.setMapBaseFieldName("refBillOfMaterialUUID");
		searchNodeConfig7.setMapSourceFieldName("uuid");
		searchNodeConfig7.setBaseNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig7);
		// Search node:[itemBillOfMaterialItem]
		BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(BillOfMaterialItem.SENAME);
		searchNodeConfig8.setNodeName(BillOfMaterialItem.NODENAME);
		searchNodeConfig8.setNodeInstID("itemBillOfMaterialItem");
		searchNodeConfig8.setStartNodeFlag(false);
		searchNodeConfig8
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig8.setMapBaseFieldName("refBOMItemUUID");
		searchNodeConfig8.setMapSourceFieldName("uuid");
		searchNodeConfig8.setBaseNodeInstID(RepairProdOrderItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig8);
		// Search node:[warehouse]
		BSearchNodeComConfigure searchNodeConfig9 = new BSearchNodeComConfigure();
		searchNodeConfig9.setSeName(Warehouse.SENAME);
		searchNodeConfig9.setNodeName(Warehouse.NODENAME);
		searchNodeConfig9.setNodeInstID(Warehouse.SENAME);
		searchNodeConfig9.setStartNodeFlag(false);
		searchNodeConfig9
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig9.setBaseNodeInstID(ProdOrderSupplyWarehouse.NODENAME);
		searchNodeConfigList.add(searchNodeConfig9);

		// Search node:[outMaterialStockKeepUnit]
		BSearchNodeComConfigure searchNodeConfig10 = new BSearchNodeComConfigure();
		searchNodeConfig10.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig10.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig10
				.setNodeInstID(RepairProdOrderSearchModel.NODE_OutMaterialStockKeepUnit);
		searchNodeConfig10.setStartNodeFlag(false);
		searchNodeConfig10
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig10.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig10.setMapSourceFieldName("uuid");
		searchNodeConfig10.setBaseNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfigList.add(searchNodeConfig10);
		return searchNodeConfigList;
	}

	@Override
	public List<BSearchNodeComConfigure> getBasicItemSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<>();
		// Search node Array :[RepairProdTargetMatItem->Material]
		List<BSearchNodeComConfigure> repairProdTargetMatItemSearchConfigureList =
				searchDocConfigHelper.genDocMatItemSearchNodeConfigureList(new SearchDocConfigHelper.SearchDocMatItemConfigureUnion(
						null,
						RepairProdTargetMatItem.SENAME,
						RepairProdTargetMatItem.NODENAME,
						RepairProdTargetMatItem.NODENAME
				));
		searchNodeConfigList.addAll(repairProdTargetMatItemSearchConfigureList);

		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(RepairProdOrder.SENAME);
		searchNodeConfig1.setNodeName(RepairProdOrder.NODENAME);
		searchNodeConfig1.setNodeInstID(RepairProdOrder.SENAME);
		searchNodeConfig1.setStartNodeFlag(false);
		searchNodeConfig1
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_CHILD);
		searchNodeConfig1
				.setBaseNodeInstID(RepairProdTargetMatItem.NODENAME);
		searchNodeConfigList.add(searchNodeConfig1);

		return searchNodeConfigList;
	}

}
