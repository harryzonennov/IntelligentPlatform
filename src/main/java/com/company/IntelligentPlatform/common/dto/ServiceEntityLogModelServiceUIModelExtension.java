package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceEntityLogModelUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityLogModelManager;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class ServiceEntityLogModelServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceEntityLogItemServiceUIModelExtension serviceEntityLogItemServiceUIModelExtension;

	@Autowired
	protected LogonUserManager logonUserManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceEntityLogItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceEntityLogModelExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceEntityLogModelExtensionUnion
				.setNodeInstId(ServiceEntityLogModel.SENAME);
		serviceEntityLogModelExtensionUnion
				.setNodeName(ServiceEntityLogModel.NODENAME);

		// UI Model Configure of node:[ServiceEntityLogModel]
		UIModelNodeMapConfigure serviceEntityLogModelMap = new UIModelNodeMapConfigure();
		serviceEntityLogModelMap.setSeName(ServiceEntityLogModel.SENAME);
		serviceEntityLogModelMap.setNodeName(ServiceEntityLogModel.NODENAME);
		serviceEntityLogModelMap.setNodeInstID(ServiceEntityLogModel.SENAME);
		serviceEntityLogModelMap.setHostNodeFlag(true);
		Class<?>[] serviceEntityLogModelConvToUIParas = {
				ServiceEntityLogModel.class, ServiceEntityLogModelUIModel.class };
		serviceEntityLogModelMap
				.setConvToUIMethodParas(serviceEntityLogModelConvToUIParas);
		serviceEntityLogModelMap
				.setConvToUIMethod(ServiceEntityLogModelManager.METHOD_ConvServiceEntityLogModelToUI);
		Class<?>[] ServiceEntityLogModelConvUIToParas = {
				ServiceEntityLogModelUIModel.class, ServiceEntityLogModel.class };
		serviceEntityLogModelMap
				.setConvUIToMethodParas(ServiceEntityLogModelConvUIToParas);
		serviceEntityLogModelMap
				.setConvUIToMethod(ServiceEntityLogModelManager.METHOD_ConvUIToServiceEntityLogModel);
		uiModelNodeMapList.add(serviceEntityLogModelMap);

		// UI Model Configure of node:[LogonUser]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.SENAME);
		logonUserMap.setBaseNodeInstID(ServiceEntityLogModel.SENAME);
		logonUserMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		logonUserMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> logonUserConditionList = new ArrayList<>();
		SearchConfigConnectCondition logonUserCondition0 = new SearchConfigConnectCondition();
		logonUserCondition0
				.setSourceFieldName(IServiceEntityNodeFieldConstant.CREATEDBY);
		logonUserCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		logonUserConditionList.add(logonUserCondition0);
		logonUserMap.setConnectionConditions(logonUserConditionList);
		Class<?>[] logonUserConvToUIParas = { LogonUser.class,
				ServiceEntityLogModelUIModel.class };
		logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
		logonUserMap
				.setConvToUIMethod(ServiceEntityLogModelManager.METHOD_ConvLogonUserToUI);
		uiModelNodeMapList.add(logonUserMap);

		// UI Model Configure of node:[BidInvitationOrder]
		// UIModelNodeMapConfigure bidInvitationOrderMap = new
		// UIModelNodeMapConfigure();
		// bidInvitationOrderMap.setSeName(DocumentContent.SENAME);
		// bidInvitationOrderMap.setNodeName(BidInvitationOrder.NODENAME);
		// bidInvitationOrderMap.setNodeInstID(BidInvitationOrder.SENAME);
		// bidInvitationOrderMap.setBaseNodeInstID(ServiceEntityLogModel.SENAME);
		// bidInvitationOrderMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		// bidInvitationOrderMap.setServiceEntityManager(bidInvitationOrderManager);
		// Class<?>[] bidInvitationOrderConvToUIParas =
		// {BidInvitationOrder.class, ServiceEntityLogModelUIModel.class};
		// bidInvitationOrderMap.setConvToUIMethodParas(bidInvitationOrderConvToUIParas);
		// bidInvitationOrderMap.setConvToUIMethod(ServiceEntityLogModelManager.METHOD_ConvServiceEntityLogModelToUI);
		// uiModelNodeMapList.add(bidInvitationOrderMap);
		serviceEntityLogModelExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceEntityLogModelExtensionUnion);
		return resultList;
	}

}
