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

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.AuthorizationGroupServiceModel;
import com.company.IntelligentPlatform.common.dto.AuthorizationGroupServiceUIModel;
import com.company.IntelligentPlatform.common.dto.AuthorizationGroupServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "authorizationGroupEditorController")
@RequestMapping(value = "/authorizationGroup")
public class AuthorizationGroupEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected AuthorizationGroupServiceUIModelExtension authorizationGroupServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				AuthorizationGroupServiceUIModel.class,
				AuthorizationGroupServiceModel.class, AOID_RESOURCE,
				AuthorizationGroup.NODENAME,
				AuthorizationGroup.SENAME, authorizationGroupServiceUIModelExtension,
				authorizationGroupManager
		);
	}
	
	@RequestMapping(value = "/getCrossGroupProcessTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCrossGroupProcessTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationGroupManager.initCrossGroupProcessTypeMap(lanCode));
	}
	
	@RequestMapping(value = "/getInnerProcessTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInnerProcessTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationGroupManager.initInnerProcessTypeMap(lanCode));
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				authorizationGroupManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLockService(
			@RequestBody SimpleSEJSONRequest request) {
		return preLock(request.getUuid());
	}

	private AuthorizationGroupServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		AuthorizationGroupServiceUIModel authorizationGroupServiceUIModel =
				(AuthorizationGroupServiceUIModel) JSONObject
				.toBean(jsonObject, AuthorizationGroupServiceUIModel.class,
						classMap);
		return authorizationGroupServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		AuthorizationGroupServiceUIModel authorizationGroupServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				authorizationGroupServiceUIModel,
				authorizationGroupServiceUIModel.getAuthorizationGroupUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						AuthorizationGroup.SENAME, AuthorizationGroup.NODENAME,
						null), ISystemActionCode.ACID_EDIT);
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

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getCrossGroupProcessType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCrossGroupProcessType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationGroupManager.initCrossGroupProcessTypeMap(lanCode));

	}

	@RequestMapping(value = "/getInnerProcessType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getInnerProcessType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationGroupManager.initInnerProcessTypeMap(lanCode));
	}

}
