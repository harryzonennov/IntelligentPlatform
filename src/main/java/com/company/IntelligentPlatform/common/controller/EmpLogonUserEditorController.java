package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.dto.EmpLogonUserServiceUIModel;
import com.company.IntelligentPlatform.common.dto.EmpLogonUserServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.EmpLogonUserManager;
import com.company.IntelligentPlatform.common.service.EmployeeManager;
import com.company.IntelligentPlatform.common.service.EmpLogonUserServiceModel;
import com.company.IntelligentPlatform.common.service.EmployeeSpecifier;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "empLogonUserEditorController")
@RequestMapping(value = "/empLogonUser")
public class EmpLogonUserEditorController {

	@Autowired
	protected EmployeeManager employeeManager;

	@Autowired
	protected EmpLogonUserManager empLogonUserManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected EmployeeSpecifier employeeSpecifier;

	@Autowired
	protected EmpLogonUserServiceUIModelExtension empLogonUserServiceUIModelExtension;

	public static final String AOID_RESOURCE = EmployeeEditorController.AOID_RESOURCE;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				EmpLogonUserServiceUIModel.class,
				EmpLogonUserServiceModel.class, AOID_RESOURCE,
				EmpLogonUserReference.NODENAME,
				EmpLogonUserReference.SENAME, empLogonUserServiceUIModelExtension,
				employeeManager
		);
	}

	@RequestMapping(value = "/getMainFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMainFlagMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> empLogonUserManager.initMainFlagMap(logonActionController.getLanguageCode()));
	}

	private EmpLogonUserServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (EmpLogonUserServiceUIModel) JSONObject
				.toBean(jsonObject, EmpLogonUserServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		EmpLogonUserServiceUIModel empLogonUserServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				empLogonUserServiceUIModel, serviceModule -> empLogonUserManager.preCheckDuplicate((EmpLogonUserServiceModel) serviceModule,
						logonActionController.getSerialLogonInfo()), serviceModule ->
						empLogonUserManager.updateUniqueDefaultLogonUser((EmpLogonUserServiceModel) serviceModule, logonActionController.getSerialLogonInfo()), null,
				empLogonUserServiceUIModel.getEmpLogonUserUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						EmpLogonUserReference.SENAME, EmpLogonUserReference.NODENAME, Employee.NODENAME,
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
