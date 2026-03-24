package com.company.IntelligentPlatform.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingServiceUIModel;
import com.company.IntelligentPlatform.common.dto.NavigationSystemSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceUIModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "navigationSystemSettingEditorController")
@RequestMapping(value = "/navigationSystemSetting")
public class NavigationSystemSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected NavigationSystemSettingServiceUIModelExtension navigationSystemSettingServiceUIModelExtension;

	@Autowired
	protected NavigationSystemSettingActionExecutionProxy navigationSystemSettingActionExecutionProxy;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				NavigationSystemSettingServiceUIModel.class,
				NavigationSystemSettingServiceModel.class, AOID_RESOURCE,
				NavigationSystemSetting.NODENAME, NavigationSystemSetting.SENAME, navigationSystemSettingServiceUIModelExtension,
				navigationSystemSettingManager, NavigationSystemSettingActionNode.NODENAME, navigationSystemSettingActionExecutionProxy
		);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	protected NavigationSystemSettingServiceUIModel refreshLoadServiceUIModel(NavigationSystemSetting navigationSystemSetting,
																		String acId)
			throws ServiceEntityConfigureException, ServiceModuleProxyException, ServiceUIModuleProxyException,
			AuthorizationException, LogonInfoException, DocActionException {
		return (NavigationSystemSettingServiceUIModel) serviceBasicUtilityController.refreshLoadServiceUIModel(
				getDocUIModelRequest(),
				navigationSystemSetting, acId);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<NavigationSystemSettingServiceModel>) (navigationSystemSettingServiceModel, actionCode, logonInfo) -> {
					try {
						navigationSystemSettingActionExecutionProxy.executeActionCore(navigationSystemSettingServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						NavigationSystemSetting.SENAME, NavigationSystemSetting.NODENAME,
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.navigationSystemSettingActionExecutionProxy);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				navigationSystemSettingManager);
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

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> navigationSystemSettingManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getApplicationLevel", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getApplicationLevel() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> navigationSystemSettingManager.initApplicationLevelMap(lanCode));
	}

	@RequestMapping(value = "/getSystemCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSystemCategory() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> navigationSystemSettingManager.initSystemCategoryMap(lanCode));
	}

}
