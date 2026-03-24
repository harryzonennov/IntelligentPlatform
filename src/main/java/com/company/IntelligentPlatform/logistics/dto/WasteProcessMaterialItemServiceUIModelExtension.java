package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.WasteProcessMaterialItemManager;
import com.company.IntelligentPlatform.logistics.service.WasteProcessOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class WasteProcessMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WasteProcessOrderManager wasteProcessOrderManager;
	
	@Autowired
	protected WasteProcessMaterialItemManager wasteProcessMaterialItemManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected LogonUserManager logonUserManager;
	
	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				WasteProcessMaterialItemAttachment.SENAME,
				WasteProcessMaterialItemAttachment.NODENAME,
				WasteProcessMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion wasteProcessMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		wasteProcessMaterialItemExtensionUnion
				.setNodeInstId(WasteProcessMaterialItem.NODENAME);
		wasteProcessMaterialItemExtensionUnion
		.setNodeName(WasteProcessMaterialItem.NODENAME);

		// UI Model Configure of node:[WasteProcessMaterialItem]
		UIModelNodeMapConfigure wasteProcessMaterialItemMap = new UIModelNodeMapConfigure();
		wasteProcessMaterialItemMap
				.setSeName(WasteProcessMaterialItem.SENAME);
		wasteProcessMaterialItemMap
				.setNodeName(WasteProcessMaterialItem.NODENAME);
		wasteProcessMaterialItemMap
				.setNodeInstID(WasteProcessMaterialItem.NODENAME);
		wasteProcessMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] wasteProcessMaterialItemConvToUIParas = {
				WasteProcessMaterialItem.class,
				WasteProcessMaterialItemUIModel.class };
		wasteProcessMaterialItemMap
				.setConvToUIMethodParas(wasteProcessMaterialItemConvToUIParas);
		wasteProcessMaterialItemMap.setLogicManager(wasteProcessMaterialItemManager);
		wasteProcessMaterialItemMap
				.setConvToUIMethod(WasteProcessMaterialItemManager.METHOD_ConvWasteProcessMaterialItemToUI);
		Class<?>[] WasteProcessMaterialItemConvUIToParas = {
				WasteProcessMaterialItemUIModel.class,
				WasteProcessMaterialItem.class };
		wasteProcessMaterialItemMap
				.setConvUIToMethodParas(WasteProcessMaterialItemConvUIToParas);
		wasteProcessMaterialItemMap
				.setConvUIToMethod(WasteProcessMaterialItemManager.METHOD_ConvUIToWasteProcessMaterialItem);
		uiModelNodeMapList.add(wasteProcessMaterialItemMap);

		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(WasteProcessMaterialItem.NODENAME));

		// UI Model Configure of node:[MaterialStockKeepUnit]
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(WasteProcessMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefReservedNodeMapConfigureList(WasteProcessMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefPrevNodeMapConfigureList(WasteProcessMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefNextNodeMapConfigureList(WasteProcessMaterialItem.NODENAME));
		uiModelNodeMapList.addAll(docFlowProxy.getDefCreateUpdateNodeMapConfigureList(WasteProcessMaterialItem.NODENAME));

		// UI Model Configure of node:[WasteProcessOrder]
		Class<?>[] convParentDocToUIMethodParas = {WasteProcessOrder.class, WasteProcessMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(WasteProcessMaterialItem.NODENAME,
				WasteProcessMaterialItemManager.METHOD_ConvParentDocToItemUI, wasteProcessMaterialItemManager,
				convParentDocToUIMethodParas));

		wasteProcessMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(wasteProcessMaterialItemExtensionUnion);
		return resultList;
	}

}
