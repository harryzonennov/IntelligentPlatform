package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialManager;
import com.company.IntelligentPlatform.common.service.MaterialTypeManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocInvolvePartyProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

@Service
public class MaterialServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected MaterialUnitServiceUIModelExtension materialUnitServiceUIModelExtension;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(materialUnitServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				MaterialAttachment.SENAME,
				MaterialAttachment.NODENAME,
				MaterialAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialActionLog.SENAME,
				MaterialActionLog.NODENAME,
				MaterialActionLog.NODEINST_ACTION_ACTIVE,
				materialManager, MaterialActionLog.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialActionLog.SENAME,
				MaterialActionLog.NODENAME,
				MaterialActionLog.NODEINST_ACTION_REINIT,
				materialManager, MaterialActionLog.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialActionLog.SENAME,
				MaterialActionLog.NODENAME,
				MaterialActionLog.NODEINST_ACTION_ARCHIVE,
				materialManager, MaterialActionLog.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialActionLog.SENAME,
				MaterialActionLog.NODENAME,
				MaterialActionLog.NODEINST_ACTION_SUBMIT,
				materialManager, MaterialActionLog.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialActionLog.SENAME,
				MaterialActionLog.NODENAME,
				MaterialActionLog.NODEINST_ACTION_APPROVE,
				materialManager, MaterialActionLog.DOC_ACTION_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder materialExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(Material.class,
					MaterialUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethodParas(Material.class,
                    MaterialUIModel.class, List.class).convToUIMethod(MaterialManager.METHOD_ConvMaterialToUI).convUIToMethod(MaterialManager.METHOD_ConvUIToMaterial));
			materialExtensionUnionBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(MaterialType.class,MaterialUIModel.class).serviceEntityManager(materialTypeManager)
					.addConnectionCondition("refMaterialType").convToUIMethod(MaterialManager.METHOD_ConvMaterialTypeToUI).baseNodeInstId(Material.SENAME));
			resultList.add(materialExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException ex) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialExtensionUnion = new ServiceUIModelExtensionUnion();
		materialExtensionUnion.setNodeInstId(Material.SENAME);
		materialExtensionUnion.setNodeName(Material.NODENAME);

		// UI Model Configure of node:[Material]
		UIModelNodeMapConfigure materialMap = new UIModelNodeMapConfigure();
		materialMap.setSeName(Material.SENAME);
		materialMap.setNodeName(Material.NODENAME);
		materialMap.setNodeInstID(Material.SENAME);
		materialMap.setHostNodeFlag(true);
		Class<?>[] materialConvToUIParas = { Material.class,
				MaterialUIModel.class, List.class };
		materialMap.setConvToUIMethodParas(materialConvToUIParas);
		materialMap.setConvToUIMethod(MaterialManager.METHOD_ConvMaterialToUI);
		Class<?>[] MaterialConvUIToParas = { MaterialUIModel.class,
				Material.class };
		materialMap.setConvUIToMethodParas(MaterialConvUIToParas);
		materialMap.setConvUIToMethod(MaterialManager.METHOD_ConvUIToMaterial);
		uiModelNodeMapList.add(materialMap);

		// UI Model Configure of node:[MaterialType]
		UIModelNodeMapConfigure materialTypeMap = new UIModelNodeMapConfigure();
		materialTypeMap.setSeName(MaterialType.SENAME);
		materialTypeMap.setNodeName(MaterialType.NODENAME);
		materialTypeMap.setNodeInstID(MaterialType.SENAME);
		materialTypeMap.setBaseNodeInstID(Material.SENAME);
		materialTypeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialTypeMap.setServiceEntityManager(materialTypeManager);
		List<SearchConfigConnectCondition> materialTypeConditionList = new ArrayList<>();
		SearchConfigConnectCondition materialTypeCondition0 = new SearchConfigConnectCondition();
		materialTypeCondition0.setSourceFieldName("refMaterialType");
		materialTypeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialTypeConditionList.add(materialTypeCondition0);
		materialTypeMap.setConnectionConditions(materialTypeConditionList);
		Class<?>[] materialTypeConvToUIParas = { MaterialType.class,
				MaterialUIModel.class };
		materialTypeMap.setConvToUIMethodParas(materialTypeConvToUIParas);
		materialTypeMap
				.setConvToUIMethod(MaterialManager.METHOD_ConvMaterialTypeToUI);
		uiModelNodeMapList.add(materialTypeMap);


		UIModelNodeMapConfigure corporateSupplierConfigure = new UIModelNodeMapConfigure();
		corporateSupplierConfigure.setSeName(CorporateCustomer.SENAME);
		corporateSupplierConfigure.setNodeName(CorporateCustomer.NODENAME);
		corporateSupplierConfigure.setNodeInstID(CorporateCustomer.SENAME);
		corporateSupplierConfigure
				.setBaseNodeInstID(Material.SENAME);
		corporateSupplierConfigure
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] convToUIParas = {
				CorporateCustomer.class, MaterialUnitUIModel.class };
		corporateSupplierConfigure
				.setConvToUIMethodParas(convToUIParas);
		corporateSupplierConfigure.setConvToUIMethod(MaterialManager.METHOD_ConvSupplierToUI);
		corporateSupplierConfigure.setMapBaseFieldName("refMainSupplierUUID");
		corporateSupplierConfigure.setMapFieldName(IServiceEntityNodeFieldConstant.UUID);
		uiModelNodeMapList.add(corporateSupplierConfigure);

		materialExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialExtensionUnion);
		return resultList;
	}


}
