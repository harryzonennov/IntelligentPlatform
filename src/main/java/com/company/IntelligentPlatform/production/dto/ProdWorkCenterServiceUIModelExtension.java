package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdWorkCenterUIModel;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ProdWorkCenterServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ProdWorkCenterResItemServiceUIModelExtension prodWorkCenterResItemServiceUIModelExtension;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;

	@Autowired
	protected ProdWorkCenterCalendarItemServiceUIModelExtension prodWorkCenterCalendarItemServiceUIModelExtension;

	@Autowired
	protected OrganizationManager organizationManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(prodWorkCenterResItemServiceUIModelExtension);
		resultList.add(prodWorkCenterCalendarItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodWorkCenterExtensionUnion = new ServiceUIModelExtensionUnion();
		prodWorkCenterExtensionUnion.setNodeInstId(ProdWorkCenter.SENAME);
		prodWorkCenterExtensionUnion.setNodeName(ProdWorkCenter.NODENAME);

		// UI Model Configure of node:[ProdWorkCenter]
		UIModelNodeMapConfigure prodWorkCenterMap = new UIModelNodeMapConfigure();
		prodWorkCenterMap.setSeName(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setNodeName(ProdWorkCenter.NODENAME);
		prodWorkCenterMap.setNodeInstID(ProdWorkCenter.SENAME);
		prodWorkCenterMap.setHostNodeFlag(true);
		Class<?>[] prodWorkCenterConvToUIParas = { ProdWorkCenter.class,
				ProdWorkCenterUIModel.class };
		prodWorkCenterMap.setConvToUIMethodParas(prodWorkCenterConvToUIParas);
		prodWorkCenterMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvProdWorkCenterToUI);
		Class<?>[] ProdWorkCenterConvUIToParas = { ProdWorkCenterUIModel.class,
				ProdWorkCenter.class };
		prodWorkCenterMap.setConvUIToMethodParas(ProdWorkCenterConvUIToParas);
		prodWorkCenterMap
				.setConvUIToMethod(ProdWorkCenterManager.METHOD_ConvUIToProdWorkCenter);
		uiModelNodeMapList.add(prodWorkCenterMap);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure parentOrganizationMap = new UIModelNodeMapConfigure();
		parentOrganizationMap.setSeName(Organization.SENAME);
		parentOrganizationMap.setNodeName(Organization.NODENAME);
		parentOrganizationMap.setNodeInstID("ParentOrganization");
		parentOrganizationMap.setBaseNodeInstID(ProdWorkCenter.SENAME);
		parentOrganizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		parentOrganizationMap.setServiceEntityManager(organizationManager);
		List<SearchConfigConnectCondition> parentOrganizationConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentOrganizationCondition0 = new SearchConfigConnectCondition();
		parentOrganizationCondition0
				.setSourceFieldName("parentOrganizationUUID");
		parentOrganizationCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentOrganizationConditionList.add(parentOrganizationCondition0);
		parentOrganizationMap
				.setConnectionConditions(parentOrganizationConditionList);
		Class<?>[] parentOrganizationConvToUIParas = { Organization.class,
				ProdWorkCenterUIModel.class };
		parentOrganizationMap
				.setConvToUIMethodParas(parentOrganizationConvToUIParas);
		parentOrganizationMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvParentOrganizationToUI);
		uiModelNodeMapList.add(parentOrganizationMap);

		// UI Model Configure of node:[Organization]
		UIModelNodeMapConfigure organizationMap = new UIModelNodeMapConfigure();
		organizationMap.setSeName(Organization.SENAME);
		organizationMap.setNodeName(Organization.NODENAME);
		organizationMap.setNodeInstID(Organization.SENAME);
		organizationMap.setBaseNodeInstID(ProdWorkCenter.SENAME);
		organizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		organizationMap.setServiceEntityManager(organizationManager);
		List<SearchConfigConnectCondition> organizationConditionList = new ArrayList<>();
		SearchConfigConnectCondition organizationCondition0 = new SearchConfigConnectCondition();
		organizationCondition0.setSourceFieldName("refCostCenterUUID");
		organizationCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		organizationConditionList.add(organizationCondition0);
		organizationMap.setConnectionConditions(organizationConditionList);
		Class<?>[] organizationConvToUIParas = { Organization.class,
				ProdWorkCenterUIModel.class };
		organizationMap.setConvToUIMethodParas(organizationConvToUIParas);
		organizationMap
				.setConvToUIMethod(ProdWorkCenterManager.METHOD_ConvRefCostCenterToUI);
		uiModelNodeMapList.add(organizationMap);
		prodWorkCenterExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodWorkCenterExtensionUnion);
		return resultList;
	}

}
