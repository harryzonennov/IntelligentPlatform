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
import com.company.IntelligentPlatform.common.dto.*;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "authorizationObjectEditorController")
@RequestMapping(value = "/authorizationObject")
public class AuthorizationObjectEditorController extends SEEditorController {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected AuthorizationObjectServiceUIModelExtension authorizationObjectServiceUIModelExtension;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	public static final String AOID_RESOURCE = IServiceModelConstants.AuthorizationObject;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				AuthorizationObjectServiceUIModel.class,
				AuthorizationObjectServiceModel.class, AOID_RESOURCE,
				AuthorizationObject.NODENAME,
				AuthorizationObject.SENAME, authorizationObjectServiceUIModelExtension,
				authorizationObjectManager
		);
	}

	@RequestMapping(value = "/getAuthorizationObjectTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAuthorizationObjectTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> authorizationObjectManager.initAuthorizationObjectTypeMap(lanCode));
	}

	@RequestMapping(value = "/getSubSystemAuthorNeedMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSubSystemAuthorNeedMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardSwitchProxy.getSimpleSwitchMap(lanCode));
	}

	private AuthorizationObjectServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		AuthorizationObjectServiceUIModel authorizationObjectServiceUIModel = (AuthorizationObjectServiceUIModel) JSONObject
				.toBean(jsonObject, AuthorizationObjectServiceUIModel.class,
						classMap);
		return authorizationObjectServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		AuthorizationObjectServiceUIModel authorizationObjectServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				authorizationObjectServiceUIModel,
				authorizationObjectServiceUIModel.getAuthorizationObjectUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						AuthorizationObject.SENAME, AuthorizationObject.NODENAME,
						null), ISystemActionCode.ACID_EDIT);
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

}
