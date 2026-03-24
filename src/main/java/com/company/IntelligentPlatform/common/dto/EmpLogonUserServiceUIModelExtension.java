package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.EmpLogonUserManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.LogonUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpLogonUserServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected EmpLogonUserManager empLogonUserManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion empLogonUserExtensionUnion = new ServiceUIModelExtensionUnion();
		empLogonUserExtensionUnion
				.setNodeInstId(EmpLogonUserReference.NODENAME);
		empLogonUserExtensionUnion
				.setNodeName(EmpLogonUserReference.NODENAME);

		// UI Model Configure of node:[EmpLogonUserReference]
		UIModelNodeMapConfigure empLogonUserReferenceMap = new UIModelNodeMapConfigure();
		empLogonUserReferenceMap.setSeName(EmpLogonUserReference.SENAME);
		empLogonUserReferenceMap.setNodeName(EmpLogonUserReference.NODENAME);
		empLogonUserReferenceMap.setNodeInstID(EmpLogonUserReference.NODENAME);
		empLogonUserReferenceMap.setLogicManager(empLogonUserManager);
		Class<?>[] empLogonUserRefConvToUIParas = { EmpLogonUserReference.class,
				EmpLogonUserUIModel.class };
		empLogonUserReferenceMap
				.setConvToUIMethodParas(empLogonUserRefConvToUIParas);
		empLogonUserReferenceMap
				.setConvToUIMethod(EmpLogonUserManager.METHOD_ConvEmpLogonToEmpLogonUserUI);
		empLogonUserReferenceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);

		Class<?>[] empLogonUserRefConvUIToParas = { EmpLogonUserUIModel.class,
				EmpLogonUserReference.class };
		empLogonUserReferenceMap
				.setConvUIToMethodParas(empLogonUserRefConvUIToParas);
		empLogonUserReferenceMap
				.setConvUIToMethod(EmpLogonUserManager.METHOD_ConvUIToEmpLogonUserReference);
		uiModelNodeMapList.add(empLogonUserReferenceMap);

		// UI Model Configure of node:[LogonUser]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.SENAME);
		logonUserMap.setBaseNodeInstID(EmpLogonUserReference.NODENAME);
		logonUserMap.setLogicManager(empLogonUserManager);
		logonUserMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		logonUserMap.setServiceEntityManager(logonUserManager);
		Class<?>[] logonUserConvToUIParas = { LogonUser.class,
				EmpLogonUserUIModel.class };
		logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
		logonUserMap
				.setConvToUIMethod(EmpLogonUserManager.METHOD_ConvLogonUserToEmpLogonUserUI);
		uiModelNodeMapList.add(logonUserMap);

		empLogonUserExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(empLogonUserExtensionUnion);
		return resultList;
	}

}
