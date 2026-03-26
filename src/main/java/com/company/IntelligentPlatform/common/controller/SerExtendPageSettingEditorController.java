package com.company.IntelligentPlatform.common.controller;

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
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingActionExecutionProxy;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingManager;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingServiceModel;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendPageSettingActionNode;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Scope("session")
@Controller(value = "serExtendPageSettingEditorController")
@RequestMapping(value = "/serExtendPageSetting")
public class SerExtendPageSettingEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SerExtendPageSettingServiceUIModelExtension serExtendPageSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;
	
	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected SerExtendPageSettingActionExecutionProxy serExtendPageSettingActionExecutionProxy;

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				SerExtendPageSettingServiceUIModel.class,
				SerExtendPageSettingServiceModel.class, AOID_RESOURCE,
				SerExtendPageSetting.NODENAME, SerExtendPageSetting.SENAME, serExtendPageSettingServiceUIModelExtension,
				serviceExtensionSettingManager, SerExtendPageSettingActionNode.NODENAME, serExtendPageSettingActionExecutionProxy
		);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageHeaderModelList(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> serExtendPageSettingManager.getPageHeaderModelList(request, client));
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(SerExtendPageSetting.SENAME, SerExtendPageSetting.NODENAME,
						null, ServiceExtensionSetting.NODENAME, request.getBaseUUID(), null, null, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serviceExtensionSettingManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewById", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewById(String id) {
		return serviceBasicUtilityController.loadModuleViewService(id,
				IServiceEntityNodeFieldConstant.ID, ISystemActionCode.ACID_EDIT,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getPageCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serExtendPageSettingManager.initPageCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getSystemCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSystemCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardSystemCategoryProxy.getSystemCategoryMap(lanCode));
	}

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serExtendPageSettingManager.initStatus(lanCode));
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.serExtendPageSettingActionExecutionProxy);
	}
	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> serExtendPageSettingActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<SerExtendPageSettingServiceModel>) (serExtendPageSettingServiceModel, actionCode, logonInfo) -> {
					try {
						serExtendPageSettingActionExecutionProxy.executeActionCore(serExtendPageSettingServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}

	public @RequestMapping(value = "/batchExecItemExclusiveHomeAction", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String batchExecItemExclusiveHomeAction(
			@RequestBody String request) {
		return serviceBasicUtilityController.batchExecItemExclusiveHomeAction(request, AOID_RESOURCE, serExtendPageSettingActionExecutionProxy,
                (docActionCode, serviceModule) -> {
                    if (docActionCode == SerExtendPageSettingActionNode.DOC_ACTION_ACTIVE) {
                        return SerExtendPageSettingActionNode.DOC_ACTION_ARCHIVE;
                    }
                    return 0;
                }, DocumentMatItemBatchGenRequest.class,
				null);
	}

}
