package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;

@Service
public class MaterialSKUUnitServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialSKUUnitManager materialSKUUnitManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				MaterialSKUUnitAttachment.SENAME,
				MaterialSKUUnitAttachment.NODENAME,
				MaterialSKUUnitAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialSKUUnitExtensionUnion = new ServiceUIModelExtensionUnion();
		materialSKUUnitExtensionUnion
				.setNodeInstId(MaterialSKUUnitReference.NODENAME);
		materialSKUUnitExtensionUnion
				.setNodeName(MaterialSKUUnitReference.NODENAME);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialSKUUnitMap = new UIModelNodeMapConfigure();
		materialSKUUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialSKUUnitMap.setNodeName(MaterialSKUUnitReference.NODENAME);
		materialSKUUnitMap.setNodeInstID(MaterialSKUUnitReference.NODENAME);
		materialSKUUnitMap.setHostNodeFlag(true);
		Class<?>[] materialSKUUnitConvToUIParas = {
				MaterialSKUUnitReference.class, MaterialSKUUnitUIModel.class };
		materialSKUUnitMap.setConvToUIMethodParas(materialSKUUnitConvToUIParas);
		materialSKUUnitMap.setLogicManager(materialSKUUnitManager);
		materialSKUUnitMap
				.setConvToUIMethod(MaterialSKUUnitManager.METHOD_ConvMaterialSKUUnitToUI);
		Class<?>[] MaterialSKUUnitConvUIToParas = {
				MaterialSKUUnitUIModel.class, MaterialSKUUnitReference.class };
		materialSKUUnitMap.setConvUIToMethodParas(MaterialSKUUnitConvUIToParas);
		materialSKUUnitMap
				.setConvUIToMethod(MaterialSKUUnitManager.METHOD_ConvUIToMaterialSKUUnit);
		uiModelNodeMapList.add(materialSKUUnitMap);

		// UI Model Configure of node:[MaterialUnitReference]
		UIModelNodeMapConfigure materialSKUMap = new UIModelNodeMapConfigure();
		materialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialSKUMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialSKUMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		materialSKUMap.setBaseNodeInstID(MaterialSKUUnitReference.NODENAME);
		materialSKUMap.setHostNodeFlag(false);
		Class<?>[] materialConvToUIParas = {
				MaterialStockKeepUnit.class, MaterialSKUUnitUIModel.class };
		materialSKUMap
				.setConvToUIMethodParas(materialConvToUIParas);
		materialSKUMap.setLogicManager(materialSKUUnitManager);
		materialSKUMap
				.setConvToUIMethod(MaterialSKUUnitManager.METHOD_ConvMaterialSKUToUnitUI);
		uiModelNodeMapList.add(materialSKUMap);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap.setNodeInstID(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap
				.setBaseNodeInstID(MaterialUnitReference.NODENAME);
		standardMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		standardMaterialUnitMap.setLogicManager(materialSKUUnitManager);
		Class<?>[] standardMaterialUnitConvToUIParas = {
				StandardMaterialUnit.class, MaterialSKUUnitUIModel.class };
		standardMaterialUnitMap
				.setConvToUIMethodParas(standardMaterialUnitConvToUIParas);
		standardMaterialUnitMap
				.setConvToUIMethod(MaterialSKUUnitManager.METHOD_ConvStandardMaterialUnitToUI);
		uiModelNodeMapList.add(standardMaterialUnitMap);
		
		materialSKUUnitExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialSKUUnitExtensionUnion);
		return resultList;
	}

}
