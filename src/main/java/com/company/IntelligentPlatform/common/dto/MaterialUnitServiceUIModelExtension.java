package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialUnitManager;
import com.company.IntelligentPlatform.common.service.StandardMaterialUnitManager;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialUnitAttachment;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;

@Service
public class MaterialUnitServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected MaterialUnitManager materialUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				MaterialUnitAttachment.SENAME,
				MaterialUnitAttachment.NODENAME,
				MaterialUnitAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialUnitReferenceExtensionUnion = new ServiceUIModelExtensionUnion();
		materialUnitReferenceExtensionUnion
				.setNodeInstId(MaterialUnitReference.NODENAME);
		materialUnitReferenceExtensionUnion
				.setNodeName(MaterialUnitReference.NODENAME);

		// UI Model Configure of node:[MaterialUnitReference]
		UIModelNodeMapConfigure materialUnitReferenceMap = new UIModelNodeMapConfigure();
		materialUnitReferenceMap.setSeName(MaterialUnitReference.SENAME);
		materialUnitReferenceMap.setNodeName(MaterialUnitReference.NODENAME);
		materialUnitReferenceMap.setNodeInstID(MaterialUnitReference.NODENAME);
		materialUnitReferenceMap.setHostNodeFlag(true);
		Class<?>[] materialUnitReferenceConvToUIParas = {
				MaterialUnitReference.class, MaterialUnitUIModel.class };
		materialUnitReferenceMap
				.setConvToUIMethodParas(materialUnitReferenceConvToUIParas);
		materialUnitReferenceMap.setLogicManager(materialUnitManager);
		materialUnitReferenceMap
				.setConvToUIMethod(MaterialUnitManager.METHOD_ConvMaterialUnitToUI);
		Class<?>[] MaterialUnitReferenceConvUIToParas = {
				MaterialUnitUIModel.class, MaterialUnitReference.class };
		materialUnitReferenceMap
				.setConvUIToMethodParas(MaterialUnitReferenceConvUIToParas);
		materialUnitReferenceMap
				.setConvUIToMethod(MaterialUnitManager.METHOD_ConvUIToMaterialUnit);
		uiModelNodeMapList.add(materialUnitReferenceMap);

		// UI Model Configure of node:[MaterialUnitReference]
		UIModelNodeMapConfigure materialMap = new UIModelNodeMapConfigure();
		materialMap.setSeName(Material.SENAME);
		materialMap.setNodeName(Material.NODENAME);
		materialMap.setNodeInstID(Material.SENAME);
		materialMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		materialMap.setBaseNodeInstID(MaterialUnitReference.NODENAME);
		materialMap.setHostNodeFlag(false);
		Class<?>[] materialConvToUIParas = {
				Material.class, MaterialUnitUIModel.class };
		materialMap
				.setConvToUIMethodParas(materialConvToUIParas);
		materialMap.setLogicManager(materialUnitManager);
		materialMap
				.setConvToUIMethod(MaterialUnitManager.METHOD_ConvMaterialToUnitUI);
		uiModelNodeMapList.add(materialMap);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap.setNodeInstID(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap
				.setBaseNodeInstID(MaterialUnitReference.NODENAME);
		standardMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		standardMaterialUnitMap
				.setServiceEntityManager(standardMaterialUnitManager);
		Class<?>[] standardMaterialUnitConvToUIParas = {
				StandardMaterialUnit.class, MaterialUnitUIModel.class };
		standardMaterialUnitMap
				.setConvToUIMethodParas(standardMaterialUnitConvToUIParas);
		standardMaterialUnitMap.setLogicManager(materialUnitManager);
		standardMaterialUnitMap
				.setConvToUIMethod(MaterialUnitManager.METHOD_ConvStandardMaterialUnitToUI);
		uiModelNodeMapList.add(standardMaterialUnitMap);
		materialUnitReferenceExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialUnitReferenceExtensionUnion);

		return resultList;
	}

}
