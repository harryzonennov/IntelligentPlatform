package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemExecutorLogUIModel;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SystemExecutorSettingManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;

@Service
public class SystemExecutorLogServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected LogonUserManager logonUserManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemExecutorLogExtensionUnion = new ServiceUIModelExtensionUnion();
		systemExecutorLogExtensionUnion
				.setNodeInstId(SystemExecutorLog.NODENAME);
		systemExecutorLogExtensionUnion.setNodeName(SystemExecutorLog.NODENAME);

		// UI Model Configure of node:[SystemExecutorLog]
		UIModelNodeMapConfigure systemExecutorLogMap = new UIModelNodeMapConfigure();
		systemExecutorLogMap.setSeName(SystemExecutorLog.SENAME);
		systemExecutorLogMap.setNodeName(SystemExecutorLog.NODENAME);
		systemExecutorLogMap.setNodeInstID(SystemExecutorLog.NODENAME);
		systemExecutorLogMap.setHostNodeFlag(true);
		Class<?>[] systemExecutorLogConvToUIParas = { SystemExecutorLog.class,
				SystemExecutorLogUIModel.class };
		systemExecutorLogMap
				.setConvToUIMethodParas(systemExecutorLogConvToUIParas);
		systemExecutorLogMap
				.setConvToUIMethod(SystemExecutorSettingManager.METHOD_ConvSystemExecutorLogToUI);
		
		uiModelNodeMapList.add(systemExecutorLogMap);

		// UI Model Configure of node:[LogonUser]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.SENAME);
		logonUserMap.setBaseNodeInstID(SystemExecutorLog.NODENAME);
		logonUserMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		logonUserMap.setServiceEntityManager(logonUserManager);
		List<SearchConfigConnectCondition> logonUserConditionList = new ArrayList<>();
		SearchConfigConnectCondition logonUserCondition0 = new SearchConfigConnectCondition();
		logonUserCondition0.setSourceFieldName("createdBy");
		logonUserCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		logonUserConditionList.add(logonUserCondition0);
		logonUserMap.setConnectionConditions(logonUserConditionList);
		Class<?>[] logonUserConvToUIParas = { LogonUser.class,
				SystemExecutorLogUIModel.class };
		logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
		logonUserMap
				.setConvToUIMethod(SystemExecutorSettingManager.METHOD_ConvLogonUserToLogUI);
		uiModelNodeMapList.add(logonUserMap);

		systemExecutorLogExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemExecutorLogExtensionUnion);
		return resultList;
	}

}
