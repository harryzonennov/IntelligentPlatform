package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseAttachment;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class WarehouseServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected WarehouseAreaServiceUIModelExtension warehouseAreaServiceUIModelExtension;

	@Autowired
	protected WarehouseStoreSettingUIModelExtension warehouseStoreSettingUIModelExtension;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				WarehouseAttachment.SENAME,
				WarehouseAttachment.NODENAME,
				WarehouseAttachment.NODENAME
		)));
		resultList.add(warehouseAreaServiceUIModelExtension);
		resultList.add(warehouseStoreSettingUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseExtensionUnion.setNodeInstId(Warehouse.SENAME);
		warehouseExtensionUnion.setNodeName(Warehouse.NODENAME);

		// UI Model Configure of node:[Warehouse]
		UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
		warehouseMap.setSeName(Warehouse.SENAME);
		warehouseMap.setNodeName(Warehouse.NODENAME);
		warehouseMap.setNodeInstID(Warehouse.SENAME);
		warehouseMap.setHostNodeFlag(true);
		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				WarehouseUIModel.class };
		warehouseMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseMap
				.setConvToUIMethod(WarehouseManager.METHOD_ConvWarehouseToUI);
		Class<?>[] WarehouseConvUIToParas = { WarehouseUIModel.class,
				Warehouse.class };
		warehouseMap.setConvUIToMethodParas(WarehouseConvUIToParas);
		warehouseMap
				.setConvUIToMethod(WarehouseManager.METHOD_ConvUIToWarehouse);
		uiModelNodeMapList.add(warehouseMap);

		// UI Model Configure of node:[Parent Organization]
		UIModelNodeMapConfigure parentOrganizationMap = new UIModelNodeMapConfigure();
		parentOrganizationMap.setSeName(Organization.SENAME);
		parentOrganizationMap.setNodeName(Organization.NODENAME);
		parentOrganizationMap.setNodeInstID(Organization.SENAME);
		parentOrganizationMap.setBaseNodeInstID(Warehouse.SENAME);
		parentOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		parentOrganizationMap.setMapBaseFieldName(Organization.PARENT_ORG_UUID);
		parentOrganizationMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentOrganizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] parentOrganizationConvToUIParas = { ServiceEntityNode.class,
				WarehouseUIModel.class };
		parentOrganizationMap
				.setConvToUIMethodParas(parentOrganizationConvToUIParas);
		parentOrganizationMap
				.setConvToUIMethod(WarehouseManager.METHOD_ConvParentOrganizationToUI);

		uiModelNodeMapList.add(parentOrganizationMap);

		warehouseExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(warehouseExtensionUnion);
		return resultList;
	}

}
