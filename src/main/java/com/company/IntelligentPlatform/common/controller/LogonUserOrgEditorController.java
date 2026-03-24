package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.LogonUserOrgServiceUIModel;
import com.company.IntelligentPlatform.common.dto.LogonUserOrgServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.LogonUserSpecifier;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.LogonUserOrgServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.LogonUserOrgReference;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "logonUserOrgEditorController")
@RequestMapping(value = "/logonUserOrg")
public class LogonUserOrgEditorController {

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LogonUserSpecifier logonUserSpecifier;

	@Autowired
	protected LogonUserOrgServiceUIModelExtension logonUserOrgServiceUIModelExtension;

	public static final String AOID_RESOURCE = LogonUserEditorController.AOID_RESOURCE;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				LogonUserOrgServiceUIModel.class,
				LogonUserOrgServiceModel.class, AOID_RESOURCE,
				LogonUserOrgReference.NODENAME,
				LogonUserOrgReference.SENAME, logonUserOrgServiceUIModelExtension,
				logonUserManager
		);
	}

	private LogonUserOrgServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (LogonUserOrgServiceUIModel) JSONObject
				.toBean(jsonObject, LogonUserOrgServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		LogonUserOrgServiceUIModel logonUserOrgServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(), logonUserOrgServiceUIModel,
				null, null, null,
				logonUserOrgServiceUIModel.getLogonUserOrganizationUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(LogonUserOrgReference.SENAME, LogonUserOrgReference.NODENAME,
						null, LogonUser.NODENAME, request.getBaseUUID(), null, logonUserSpecifier, request, null),
				ISystemActionCode.ACID_EDIT);
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
