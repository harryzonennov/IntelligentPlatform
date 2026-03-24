package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;

@Service
public class FinAccountMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected FinAccountMatItemAttachmentServiceUIModelExtension finAccountMatItemAttachmentServiceUIModelExtension;

	@Autowired
	protected FinAccountManager finAccountManager;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(finAccountMatItemAttachmentServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion finAccountMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		finAccountMaterialItemExtensionUnion
				.setNodeInstId(FinAccountMaterialItem.NODENAME);
		finAccountMaterialItemExtensionUnion
				.setNodeName(FinAccountMaterialItem.NODENAME);

		// UI Model Configure of node:[FinAccountMaterialItem]
		UIModelNodeMapConfigure finAccountMaterialItemMap = new UIModelNodeMapConfigure();
		finAccountMaterialItemMap.setSeName(FinAccountMaterialItem.SENAME);
		finAccountMaterialItemMap.setNodeName(FinAccountMaterialItem.NODENAME);
		finAccountMaterialItemMap.setNodeInstID(FinAccountMaterialItem.NODENAME);
		finAccountMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] finAccountMaterialItemConvToUIParas = {
				FinAccountMaterialItem.class, FinAccountMaterialItemUIModel.class };
		finAccountMaterialItemMap
				.setConvToUIMethodParas(finAccountMaterialItemConvToUIParas);
		finAccountMaterialItemMap
				.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountMaterialItemToUI);
		Class<?>[] FinAccountMaterialItemConvUIToParas = {
				FinAccountMaterialItemUIModel.class, FinAccountMaterialItem.class };
		finAccountMaterialItemMap
				.setConvUIToMethodParas(FinAccountMaterialItemConvUIToParas);
		finAccountMaterialItemMap
				.setConvUIToMethod(FinAccountManager.METHOD_ConvUIToFinAccountMaterialItem);
		uiModelNodeMapList.add(finAccountMaterialItemMap);

		// UI Model Configure of node:[FinAccount]
		UIModelNodeMapConfigure finAccountMap = new UIModelNodeMapConfigure();
		finAccountMap.setSeName(FinAccount.SENAME);
		finAccountMap.setNodeName(FinAccount.NODENAME);
		finAccountMap.setNodeInstID(FinAccount.SENAME);
		finAccountMap.setBaseNodeInstID(FinAccountMaterialItem.NODENAME);
		finAccountMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] finAccountConvToUIParas = { FinAccount.class,
				FinAccountMaterialItemUIModel.class };
		finAccountMap.setConvToUIMethodParas(finAccountConvToUIParas);
		finAccountMap.setConvToUIMethod(FinAccountManager.METHOD_ConvFinAccountToItemUI);
		uiModelNodeMapList.add(finAccountMap);


		// UI Model Configure of node:[MaterialStockKeepUnit]
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, FinAccountMaterialItemUIModel.class };
		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						FinAccountMaterialItem.NODENAME,
						FinAccountManager.METHOD_ConvMaterialSKUToItemUI, null,
						materialStockKeepUnitConvToUIParas));
		
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(FinAccountMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(FinAccountMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(FinAccountMaterialItem.NODENAME));
		uiModelNodeMapList
		.addAll(docFlowProxy
				.getDefCreateUpdateNodeMapConfigureList(FinAccountMaterialItem.NODENAME));

		finAccountMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(finAccountMaterialItemExtensionUnion);
		return resultList;
	}

}
