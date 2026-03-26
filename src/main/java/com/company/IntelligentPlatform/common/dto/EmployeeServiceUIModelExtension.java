package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.OrganizationManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class EmployeeServiceUIModelExtension extends ServiceUIModelExtension {

	@Autowired
	protected EmployeeOrgServiceUIModelExtension employeeOrgServiceUIModelExtension;

	@Autowired
	protected EmpLogonUserServiceUIModelExtension empLogonUserServiceUIModelExtension;

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected OrganizationManager organizationManager;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				EmployeeAttachment.SENAME,
				EmployeeAttachment.NODENAME,
				EmployeeAttachment.NODENAME
		)));
		resultList.add(employeeOrgServiceUIModelExtension);
		resultList.add(empLogonUserServiceUIModelExtension);
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				EmployeeActionNode.SENAME,
				EmployeeActionNode.NODENAME,
				EmployeeActionNode.NODEINST_ACTION_ACTIVE,
				employeeManager, EmployeeActionNode.DOC_ACTION_ACTIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				EmployeeActionNode.SENAME,
				EmployeeActionNode.NODENAME,
				EmployeeActionNode.NODEINST_ACTION_REINIT,
				employeeManager, EmployeeActionNode.DOC_ACTION_REINIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				EmployeeActionNode.SENAME,
				EmployeeActionNode.NODENAME,
				EmployeeActionNode.NODEINST_ACTION_ARCHIVE,
				employeeManager, EmployeeActionNode.DOC_ACTION_ARCHIVE
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				EmployeeActionNode.SENAME,
				EmployeeActionNode.NODENAME,
				EmployeeActionNode.NODEINST_ACTION_SUBMIT,
				employeeManager, EmployeeActionNode.DOC_ACTION_SUBMIT
		)));
		resultList.add(docActionNodeProxy.genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(
				EmployeeActionNode.SENAME,
				EmployeeActionNode.NODENAME,
				EmployeeActionNode.NODEINST_ACTION_APPROVE,
				employeeManager, EmployeeActionNode.DOC_ACTION_APPROVE
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion employeeExtensionUnion = new ServiceUIModelExtensionUnion();
		employeeExtensionUnion.setNodeInstId(Employee.SENAME);
		employeeExtensionUnion.setNodeName(Employee.NODENAME);

		// UI Model Configure of node:[Employee]
		UIModelNodeMapConfigure employeeMap = new UIModelNodeMapConfigure();
		employeeMap.setSeName(Employee.SENAME);
		employeeMap.setNodeName(Employee.NODENAME);
		employeeMap.setNodeInstID(Employee.SENAME);
		employeeMap.setHostNodeFlag(true);
		Class<?>[] employeeConvToUIParas = { Employee.class,
				EmployeeUIModel.class };
		employeeMap.setConvToUIMethodParas(employeeConvToUIParas);
		employeeMap.setConvToUIMethod(EmployeeManager.METHOD_ConvEmployeeToUI);
		Class<?>[] EmployeeConvUIToParas = { EmployeeUIModel.class,
				Employee.class };
		employeeMap.setConvUIToMethodParas(EmployeeConvUIToParas);
		employeeMap.setConvUIToMethod(EmployeeManager.METHOD_ConvUIToEmployee);
		uiModelNodeMapList.add(employeeMap);

		// UI Model Configure of node:[EmpLogonUserReference]
		UIModelNodeMapConfigure empLogonUserReferenceMap = new UIModelNodeMapConfigure();
		empLogonUserReferenceMap.setSeName(EmpLogonUserReference.SENAME);
		empLogonUserReferenceMap.setNodeName(EmpLogonUserReference.NODENAME);
		empLogonUserReferenceMap.setNodeInstID(EmpLogonUserReference.NODENAME);
		empLogonUserReferenceMap.setBaseNodeInstID(Employee.SENAME);
		empLogonUserReferenceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_PARENT);
		empLogonUserReferenceMap.setServiceEntityManager(employeeManager);
		uiModelNodeMapList.add(empLogonUserReferenceMap);

		// UI Model Configure of node:[LogonUser]
		UIModelNodeMapConfigure logonUserMap = new UIModelNodeMapConfigure();
		logonUserMap.setSeName(LogonUser.SENAME);
		logonUserMap.setNodeName(LogonUser.NODENAME);
		logonUserMap.setNodeInstID(LogonUser.SENAME);
		logonUserMap.setBaseNodeInstID(EmpLogonUserReference.NODENAME);
		logonUserMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		logonUserMap.setServiceEntityManager(logonUserManager);
		Class<?>[] logonUserConvToUIParas = { LogonUser.class,
				EmployeeUIModel.class };
		logonUserMap.setConvToUIMethodParas(logonUserConvToUIParas);
		logonUserMap
				.setConvToUIMethod(EmployeeManager.METHOD_ConvLogonUserToUI);
		uiModelNodeMapList.add(logonUserMap);

		employeeExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(employeeExtensionUnion);
		return resultList;
	}

}
