package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceModuleConvertPara;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Scope("session")
@Controller(value = "roleSubAuthorizationEditorController")
@RequestMapping(value = "/roleSubAuthorization")
public class RoleSubAuthorizationEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected RoleSubAuthorizationServiceUIModelExtension roleSubAuthorizationServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected RoleSubAuthorizationManager roleSubAuthorizationManager;

	@Autowired
	protected RoleAuthorizationManager roleAuthorizationManager;

	@Autowired
	protected RoleManager roleManager;

	protected Logger logger = LoggerFactory.getLogger(RoleSubAuthorizationEditorController.class);

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		List<ServiceModuleConvertPara> serviceModuleConvertParaList = null;
		try {
			serviceModuleConvertParaList = roleAuthorizationManager.genActionCodeParaList();
		} catch (ServiceEntityConfigureException e) {
			// do nothing
			logger.error(e.getMessage(), e);
		}
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				RoleSubAuthorizationServiceUIModel.class,
				RoleSubAuthorizationServiceModel.class, AOID_RESOURCE,
				RoleSubAuthorization.NODENAME,
				RoleSubAuthorization.SENAME, roleSubAuthorizationServiceUIModelExtension,
				roleManager, serviceModuleConvertParaList
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> roleSubAuthorizationManager.getPageHeaderModelList(request1, client));
	}

	private RoleSubAuthorizationServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (RoleSubAuthorizationServiceUIModel) JSONObject.toBean(jsonObject,
				RoleSubAuthorizationServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		RoleSubAuthorizationServiceUIModel roleSubAuthorizationServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				roleSubAuthorizationServiceUIModel,
				roleSubAuthorizationServiceUIModel.getRoleSubAuthorizationUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(RoleSubAuthorization.SENAME, RoleSubAuthorization.NODENAME,
						RoleAuthorization.NODENAME, request.getBaseUUID()).build(),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, roleManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}



}
