package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.MaterialTypeManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class MaterialStockKeepUnitServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialSKUUnitServiceUIModelExtension materialSKUUnitServiceUIModelExtension;
	
	@Autowired
	protected MaterialSKUExtendPropertyServiceUIModelExtension materialSKUExtendPropertyServiceUIModelExtension;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialManager materialManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(materialSKUUnitServiceUIModelExtension);
		resultList.add(materialSKUExtendPropertyServiceUIModelExtension);
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				MaterialSKUAttachment.SENAME,
				MaterialSKUAttachment.NODENAME,
				MaterialSKUAttachment.NODENAME
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialSKUActionLog.SENAME,
				MaterialSKUActionLog.NODENAME,
				MaterialSKUActionLog.NODEINST_ACTION_ACTIVE,
				materialStockKeepUnitManager, MaterialSKUActionLog.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialSKUActionLog.SENAME,
				MaterialSKUActionLog.NODENAME,
				MaterialSKUActionLog.NODEINST_ACTION_SUBMIT,
				materialStockKeepUnitManager, MaterialSKUActionLog.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialSKUActionLog.SENAME,
				MaterialSKUActionLog.NODENAME,
				MaterialSKUActionLog.NODEINST_ACTION_APPROVE,
				materialStockKeepUnitManager, MaterialSKUActionLog.DOC_ACTION_APPROVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialSKUActionLog.SENAME,
				MaterialSKUActionLog.NODENAME,
				MaterialSKUActionLog.NODEINST_ACTION_REINIT,
				materialStockKeepUnitManager, MaterialSKUActionLog.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				MaterialSKUActionLog.SENAME,
				MaterialSKUActionLog.NODENAME,
				MaterialSKUActionLog.NODEINST_ACTION_ARCHIVE,
				materialStockKeepUnitManager, MaterialSKUActionLog.DOC_ACTION_ARCHIVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {

		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder materialSKUExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(MaterialStockKeepUnit.class,
					MaterialStockKeepUnitUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethodParas(MaterialStockKeepUnit.class,
							MaterialStockKeepUnitUIModel.class, List.class).convToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialSKUToUI)
							.convUIToMethod(MaterialStockKeepUnitManager.METHOD_ConvUIToMaterialSKU));
			materialSKUExtensionUnionBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(Material.class,MaterialStockKeepUnitUIModel.class)
					.serviceEntityManager(materialManager)
					.addConnectionCondition("refMaterialUUID").convToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialToUI).baseNodeInstId(MaterialStockKeepUnit.SENAME));
			materialSKUExtensionUnionBuilder.addMapConfigureBuilder(ServiceUIModelExtensionHelper.genUIConfBuilder(MaterialType.class,MaterialStockKeepUnitUIModel.class).serviceEntityManager(materialTypeManager)
					.addConnectionCondition("refMaterialType").convToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialTypeToUI).baseNodeInstId(Material.SENAME));
			resultList.add(materialSKUExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException ex) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}

	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialStockKeepUnitExtensionUnion = new ServiceUIModelExtensionUnion();
		materialStockKeepUnitExtensionUnion
				.setNodeInstId(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitExtensionUnion
				.setNodeName(MaterialStockKeepUnit.NODENAME);

		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialStockKeepUnitMap = new UIModelNodeMapConfigure();
		materialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialStockKeepUnitMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setHostNodeFlag(true);
		Class<?>[] materialStockKeepUnitConvToUIParas = {
				MaterialStockKeepUnit.class, MaterialStockKeepUnitUIModel.class, List.class };
		materialStockKeepUnitMap
				.setConvToUIMethodParas(materialStockKeepUnitConvToUIParas);
		materialStockKeepUnitMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialSKUToUI);
		Class<?>[] MaterialStockKeepUnitConvUIToParas = {
				MaterialStockKeepUnitUIModel.class, MaterialStockKeepUnit.class };
		materialStockKeepUnitMap
				.setConvUIToMethodParas(MaterialStockKeepUnitConvUIToParas);
		materialStockKeepUnitMap
				.setConvUIToMethod(MaterialStockKeepUnitManager.METHOD_ConvUIToMaterialSKU);
		uiModelNodeMapList.add(materialStockKeepUnitMap);

		// UI Model Configure of node:[CorporateSupplier]
		UIModelNodeMapConfigure corporateSupplierMap = new UIModelNodeMapConfigure();
		corporateSupplierMap.setSeName(CorporateCustomer.SENAME);
		corporateSupplierMap.setNodeName(CorporateCustomer.NODENAME);
		corporateSupplierMap.setNodeInstID("CorporateSupplier");
		corporateSupplierMap.setBaseNodeInstID(MaterialStockKeepUnit.SENAME);
		corporateSupplierMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		corporateSupplierMap.setServiceEntityManager(corporateCustomerManager);
		List<SearchConfigConnectCondition> corporateSupplierConditionList = new ArrayList<>();
		SearchConfigConnectCondition corporateSupplierCondition0 = new SearchConfigConnectCondition();
		corporateSupplierCondition0.setSourceFieldName("refSupplierUUID");
		corporateSupplierCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		corporateSupplierConditionList.add(corporateSupplierCondition0);
		corporateSupplierMap
				.setConnectionConditions(corporateSupplierConditionList);
		Class<?>[] corporateSupplierConvToUIParas = { CorporateCustomer.class,
				MaterialStockKeepUnitUIModel.class };
		corporateSupplierMap
				.setConvToUIMethodParas(corporateSupplierConvToUIParas);
		corporateSupplierMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvCorporateSupplierToUI);
		uiModelNodeMapList.add(corporateSupplierMap);

		// UI Model Configure of node:[Material]
		UIModelNodeMapConfigure materialMap = new UIModelNodeMapConfigure();
		materialMap.setSeName(Material.SENAME);
		materialMap.setNodeName(Material.NODENAME);
		materialMap.setNodeInstID(Material.SENAME);
		materialMap.setBaseNodeInstID(MaterialStockKeepUnit.SENAME);
		materialMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialMap.setServiceEntityManager(materialManager);
		List<SearchConfigConnectCondition> materialConditionList = new ArrayList<>();
		SearchConfigConnectCondition materialCondition0 = new SearchConfigConnectCondition();
		materialCondition0.setSourceFieldName("refMaterialUUID");
		materialCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialConditionList.add(materialCondition0);
		materialMap.setConnectionConditions(materialConditionList);
		Class<?>[] materialConvToUIParas = { Material.class,
				MaterialStockKeepUnitUIModel.class };
		materialMap.setConvToUIMethodParas(materialConvToUIParas);
		materialMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialToUI);
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
				MaterialStockKeepUnitUIModel.class };
		materialTypeMap.setConvToUIMethodParas(materialTypeConvToUIParas);
		materialTypeMap
				.setConvToUIMethod(MaterialStockKeepUnitManager.METHOD_ConvMaterialTypeToUI);
		uiModelNodeMapList.add(materialTypeMap);
		materialStockKeepUnitExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialStockKeepUnitExtensionUnion);
		return resultList;
	}

}
