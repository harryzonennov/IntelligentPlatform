package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProductionResourceUnionUIModel;
import com.company.IntelligentPlatform.production.service.ProductionResourceUnionManager;
import com.company.IntelligentPlatform.production.model.ProductionResourceUnion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Organization;

@Service
public class ProductionResourceUnionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected OrganizationManager organizationManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion productionResourceUnionExtensionUnion = new ServiceUIModelExtensionUnion();
		productionResourceUnionExtensionUnion
				.setNodeInstId(ProductionResourceUnion.SENAME);
		productionResourceUnionExtensionUnion
				.setNodeName(ProductionResourceUnion.NODENAME);

		// UI Model Configure of node:[ProductionResourceUnion]
		UIModelNodeMapConfigure productionResourceUnionMap = new UIModelNodeMapConfigure();
		productionResourceUnionMap.setSeName(ProductionResourceUnion.SENAME);
		productionResourceUnionMap
				.setNodeName(ProductionResourceUnion.NODENAME);
		productionResourceUnionMap
				.setNodeInstID(ProductionResourceUnion.SENAME);
		productionResourceUnionMap.setHostNodeFlag(true);
		Class<?>[] productionResourceUnionConvToUIParas = {
				ProductionResourceUnion.class,
				ProductionResourceUnionUIModel.class };
		productionResourceUnionMap
				.setConvToUIMethodParas(productionResourceUnionConvToUIParas);
		productionResourceUnionMap
				.setConvToUIMethod(ProductionResourceUnionManager.METHOD_ConvProductionResourceUnionToUI);
		Class<?>[] productionResourceUnionConvUIToParas = {
				ProductionResourceUnionUIModel.class,
				ProductionResourceUnion.class };
		productionResourceUnionMap
				.setConvUIToMethodParas(productionResourceUnionConvUIToParas);
		productionResourceUnionMap
				.setConvUIToMethod(ProductionResourceUnionManager.METHOD_ConvUIToProductionResourceUnion);
		uiModelNodeMapList.add(productionResourceUnionMap);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setBaseNodeInstID(ProductionResourceUnion.SENAME);
		organizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> organizationConditionList = new ArrayList<>();
		SearchConfigConnectCondition organizationCondition0 = new SearchConfigConnectCondition();
		organizationCondition0.setSourceFieldName("refCostCenterUUID");
		organizationCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		organizationConditionList.add(organizationCondition0);
		organizationMap.setConnectionConditions(organizationConditionList);
		organizationMap.setServiceEntityManager(organizationManager);
		Class<?>[] organizationConvToUIParas = { Organization.class,
				ProductionResourceUnionUIModel.class };
		organizationMap.setConvToUIMethodParas(organizationConvToUIParas);
		organizationMap
				.setConvToUIMethod(ProductionResourceUnionManager.METHOD_ConvCostCenterToUI);
		uiModelNodeMapList.add(organizationMap);
		productionResourceUnionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(productionResourceUnionExtensionUnion);
		return resultList;
	}

}
