package com.company.IntelligentPlatform.common.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.dto.EmployeeOrgServiceUIModel;
import com.company.IntelligentPlatform.common.dto.EmployeeOrgServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.EmployeeOrgServiceModel;
import com.company.IntelligentPlatform.common.service.EmployeeSpecifier;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;

import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.EmployeeOrgReference;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "employeeOrgEditorController")
@RequestMapping(value = "/employeeOrg")
public class EmployeeOrgEditorController {

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected EmployeeSpecifier employeeSpecifier;

	@Autowired
	protected EmployeeOrgServiceUIModelExtension employeeOrgServiceUIModelExtension;

	public static final String AOID_RESOURCE = EmployeeEditorController.AOID_RESOURCE;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				EmployeeOrgServiceUIModel.class,
				EmployeeOrgServiceModel.class, AOID_RESOURCE,
				EmployeeOrgReference.NODENAME,
				EmployeeOrgReference.SENAME, employeeOrgServiceUIModelExtension,
				employeeManager
		);
	}

	private EmployeeOrgServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (EmployeeOrgServiceUIModel) JSONObject
				.toBean(jsonObject, EmployeeOrgServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		EmployeeOrgServiceUIModel employeeOrgServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				employeeOrgServiceUIModel,
				employeeOrgServiceUIModel.getEmployeeOrgUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						EmployeeOrgReference.SENAME, EmployeeOrgReference.NODENAME, Employee.NODENAME,
						request.getBaseUUID(),
						employeeSpecifier), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

}
