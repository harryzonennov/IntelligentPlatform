package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialTypeManager;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeActionNode;
import com.company.IntelligentPlatform.common.model.MaterialTypeAttachment;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class MaterialTypeServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;
	
	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				MaterialTypeAttachment.SENAME,
				MaterialTypeAttachment.NODENAME,
				MaterialTypeAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialTypeActionNode.SENAME,
				MaterialTypeActionNode.NODENAME,
				MaterialTypeActionNode.NODEINST_ACTION_ACTIVE,
				materialTypeManager, MaterialTypeActionNode.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialTypeActionNode.SENAME,
				MaterialTypeActionNode.NODENAME,
				MaterialTypeActionNode.NODEINST_ACTION_REINIT,
				materialTypeManager, MaterialTypeActionNode.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialTypeActionNode.SENAME,
				MaterialTypeActionNode.NODENAME,
				MaterialTypeActionNode.NODEINST_ACTION_ARCHIVE,
				materialTypeManager, MaterialTypeActionNode.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialTypeActionNode.SENAME,
				MaterialTypeActionNode.NODENAME,
				MaterialTypeActionNode.NODEINST_ACTION_SUBMIT,
				materialTypeManager, MaterialTypeActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialTypeActionNode.SENAME,
				MaterialTypeActionNode.NODENAME,
				MaterialTypeActionNode.NODEINST_ACTION_APPROVE,
				materialTypeManager, MaterialTypeActionNode.DOC_ACTION_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialTypeExtensionUnion = new ServiceUIModelExtensionUnion();
		materialTypeExtensionUnion.setNodeInstId(MaterialType.SENAME);
		materialTypeExtensionUnion.setNodeName(MaterialType.NODENAME);

		// UI Model Configure of node:[MaterialType]
		UIModelNodeMapConfigure materialTypeMap = new UIModelNodeMapConfigure();
		materialTypeMap.setSeName(MaterialType.SENAME);
		materialTypeMap.setNodeName(MaterialType.NODENAME);
		materialTypeMap.setNodeInstID(MaterialType.SENAME);
		materialTypeMap.setHostNodeFlag(true);
		Class<?>[] materialTypeConvToUIParas = { MaterialType.class,
				MaterialTypeUIModel.class };
		materialTypeMap.setConvToUIMethodParas(materialTypeConvToUIParas);
		materialTypeMap
				.setConvToUIMethod(MaterialTypeManager.METHOD_ConvMaterialTypeToUI);
		Class<?>[] MaterialTypeConvUIToParas = { MaterialTypeUIModel.class,
				MaterialType.class };
		materialTypeMap.setConvUIToMethodParas(MaterialTypeConvUIToParas);
		materialTypeMap
				.setConvUIToMethod(MaterialTypeManager.METHOD_ConvUIToMaterialType);
		uiModelNodeMapList.add(materialTypeMap);

		// UI Model Configure of node:[ParentMaterialType]
		UIModelNodeMapConfigure parentMaterialTypeMap = new UIModelNodeMapConfigure();
		parentMaterialTypeMap.setSeName(MaterialType.SENAME);
		parentMaterialTypeMap.setNodeName(MaterialType.NODENAME);
		parentMaterialTypeMap.setNodeInstID("ParentMaterialType");
		parentMaterialTypeMap.setBaseNodeInstID(MaterialType.SENAME);
		parentMaterialTypeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> parentTypeConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentTypeCondition0 = new SearchConfigConnectCondition();
		parentTypeCondition0.setSourceFieldName("parentTypeUUID");
		parentTypeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentTypeConditionList.add(parentTypeCondition0);
		parentMaterialTypeMap
				.setConnectionConditions(parentTypeConditionList);
		parentMaterialTypeMap.setMapBaseFieldName("parentTypeUUID");
		parentMaterialTypeMap.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentMaterialTypeMap.setServiceEntityManager(materialTypeManager);
		Class<?>[] parentMaterialTypeConvToUIParas = { MaterialType.class,
				MaterialTypeUIModel.class };
		parentMaterialTypeMap
				.setConvToUIMethodParas(parentMaterialTypeConvToUIParas);
		parentMaterialTypeMap
				.setConvToUIMethod(MaterialTypeManager.METHOD_ConvParentMaterialTypeToUI);
		Class<?>[] ParentMaterialTypeConvUIToParas = {
				MaterialTypeUIModel.class, MaterialType.class };
		parentMaterialTypeMap
				.setConvUIToMethodParas(ParentMaterialTypeConvUIToParas);
		uiModelNodeMapList.add(parentMaterialTypeMap);
		materialTypeExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		
		// UI Model Configure of node:[RootMaterialType]
		UIModelNodeMapConfigure rootMaterialTypeMap = new UIModelNodeMapConfigure();
		rootMaterialTypeMap.setSeName(MaterialType.SENAME);
		rootMaterialTypeMap.setNodeName(MaterialType.NODENAME);
		rootMaterialTypeMap.setNodeInstID("RootMaterialType");
		rootMaterialTypeMap.setBaseNodeInstID(MaterialType.SENAME);

		List<SearchConfigConnectCondition> rootTypeConditionList = new ArrayList<>();
		SearchConfigConnectCondition rootTypeCondition0 = new SearchConfigConnectCondition();
		rootTypeCondition0.setSourceFieldName("rootTypeUUID");
		rootTypeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		rootTypeConditionList.add(rootTypeCondition0);
		rootMaterialTypeMap
				.setConnectionConditions(rootTypeConditionList);
		rootMaterialTypeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		rootMaterialTypeMap.setGetSENodeCallback(parentSENode -> materialTypeManager.getRootMaterialType((MaterialType) parentSENode, null));
		rootMaterialTypeMap.setServiceEntityManager(materialTypeManager);
		Class<?>[] rootMaterialTypeConvToUIParas = { MaterialType.class,
				MaterialTypeUIModel.class };
		rootMaterialTypeMap
				.setConvToUIMethodParas(rootMaterialTypeConvToUIParas);
		rootMaterialTypeMap
				.setConvToUIMethod(MaterialTypeManager.METHOD_ConvRootMaterialTypeToUI);
		
		uiModelNodeMapList.add(rootMaterialTypeMap);
		materialTypeExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialTypeExtensionUnion);
		return resultList;
	}

}
