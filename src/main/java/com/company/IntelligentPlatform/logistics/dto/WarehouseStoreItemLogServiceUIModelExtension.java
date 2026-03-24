package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreItemLogManager;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class WarehouseStoreItemLogServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected WarehouseStoreItemLogManager warehouseStoreItemLogManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;
    @Autowired
    private MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseStoreItemLogExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseStoreItemLogExtensionUnion
				.setNodeInstId(WarehouseStoreItemLog.NODENAME);
		warehouseStoreItemLogExtensionUnion
				.setNodeName(WarehouseStoreItemLog.NODENAME);

		// UI Model Configure of node:[WarehouseStoreItemLog]
		UIModelNodeMapConfigure warehouseStoreItemLogMap = new UIModelNodeMapConfigure();
		warehouseStoreItemLogMap.setSeName(WarehouseStoreItemLog.SENAME);
		warehouseStoreItemLogMap.setNodeName(WarehouseStoreItemLog.NODENAME);
		warehouseStoreItemLogMap.setNodeInstID(WarehouseStoreItemLog.NODENAME);
		warehouseStoreItemLogMap.setHostNodeFlag(true);
		warehouseStoreItemLogMap.setLogicManager(warehouseStoreItemLogManager);
		Class<?>[] warehouseStoreItemLogConvToUIParas = {
				WarehouseStoreItemLog.class, WarehouseStoreItemLogUIModel.class };
		warehouseStoreItemLogMap
				.setConvToUIMethodParas(warehouseStoreItemLogConvToUIParas);
		warehouseStoreItemLogMap
				.setConvToUIMethod(WarehouseStoreItemLogManager.METHOD_ConvWarehouseStoreItemLogToUI);
		Class<?>[] WarehouseStoreItemLogConvUIToParas = {
				WarehouseStoreItemLogUIModel.class, WarehouseStoreItemLog.class };
		warehouseStoreItemLogMap
				.setConvUIToMethodParas(WarehouseStoreItemLogConvUIToParas);
		uiModelNodeMapList.add(warehouseStoreItemLogMap);

		Class<?>[] documentConvToUIParas = { ServiceEntityNode.class,
				WarehouseStoreItemLogUIModel.class };
		DocFlowProxy.SimpleDocConfigurePara simpleDocConfigurePara = new DocFlowProxy.SimpleDocConfigurePara(
				WarehouseStoreItemLog.NODENAME,
				WarehouseStoreItemLogManager.METHOD_ConvDocumentToStoreItemLogUI,
				documentConvToUIParas, warehouseStoreItemLogManager);
		simpleDocConfigurePara
				.setDocMatItemGetCallback(rawSENode -> {
					WarehouseStoreItemLog warehouseStoreItemLog = (WarehouseStoreItemLog) rawSENode;
					int documentType = warehouseStoreItemLog.getDocumentType();
					ServiceEntityNode specDocMatItemNode = docFlowProxy
							.getDefDocItemNode(documentType,
									warehouseStoreItemLog.getDocumentUUID(),
									warehouseStoreItemLog.getClient());
					return specDocMatItemNode;
				});
		uiModelNodeMapList.addAll(docFlowProxy
				.getSpecNodeMapConfigureList(simpleDocConfigurePara));

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialStockKeepUnitMap = new UIModelNodeMapConfigure();
		materialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialStockKeepUnitMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap
				.setBaseNodeInstID(WarehouseStoreItemLog.NODENAME);
		materialStockKeepUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialStockKeepUnitMap
				.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> refMaterialStockKeepUnitConditionList = new ArrayList<>();
		SearchConfigConnectCondition refMaterialStockKeepUnitCondition0 = new SearchConfigConnectCondition();
		refMaterialStockKeepUnitCondition0.setSourceFieldName("refMaterialSKUUUID");
		refMaterialStockKeepUnitCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		refMaterialStockKeepUnitConditionList.add(refMaterialStockKeepUnitCondition0);
		materialStockKeepUnitMap
				.setConnectionConditions(refMaterialStockKeepUnitConditionList);
		Class<?>[] refMaterialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class,
				WarehouseStoreItemLogUIModel.class };
		materialStockKeepUnitMap
				.setConvToUIMethodParas(refMaterialStockKeepUnitConvToUIParas);
		materialStockKeepUnitMap.setLogicManager(warehouseStoreItemLogManager);
		materialStockKeepUnitMap
				.setConvToUIMethod(WarehouseStoreItemLogManager.METHOD_ConvMaterialStockKeepUnitToUI);
		uiModelNodeMapList.add(materialStockKeepUnitMap);

		warehouseStoreItemLogExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(warehouseStoreItemLogExtensionUnion);
		return resultList;
	}

}
