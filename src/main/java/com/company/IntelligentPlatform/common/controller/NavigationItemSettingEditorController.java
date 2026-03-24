package com.company.IntelligentPlatform.common.controller;

import java.lang.SuppressWarnings;
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
import com.company.IntelligentPlatform.common.dto.NavigationItemSettingServiceUIModel;
import com.company.IntelligentPlatform.common.dto.NavigationItemSettingServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.*;

@Scope("session")
@Controller(value = "navigationItemSettingEditorController")
@RequestMapping(value = "/navigationItemSetting")
public class NavigationItemSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected NavigationItemSettingServiceUIModelExtension navigationItemSettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected NavigationItemSettingManager navigationItemSettingManager;

    @Autowired
    private NavigationSystemSettingSpecifier navigationSystemSettingSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				NavigationItemSettingServiceUIModel.class,
				NavigationItemSettingServiceModel.class, AOID_RESOURCE,
				NavigationItemSetting.NODENAME,
				NavigationItemSetting.SENAME, navigationItemSettingServiceUIModelExtension,
				navigationSystemSettingManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> navigationItemSettingManager.getPageHeaderModelList(request1, client));
	}

	private NavigationItemSettingServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (NavigationItemSettingServiceUIModel) JSONObject
				.toBean(jsonObject, NavigationItemSettingServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		NavigationItemSettingServiceUIModel navigationItemSettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				navigationItemSettingServiceUIModel,
				navigationItemSettingServiceUIModel.getNavigationItemSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
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

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						NavigationItemSetting.SENAME, NavigationItemSetting.NODENAME,
						NavigationGroupSetting.NODENAME, request.getBaseUUID(), null,
						(baseUUID, parentNodeName, client) -> navigationItemSettingManager.newNavigationItemFromParent(baseUUID, client)), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest(),
				serviceEntityNode -> navigationSystemSettingSpecifier.deleteSubItemListRecursive(uuid, NavigationItemSetting.FIELD_PARENT_ELEMENT_UUID,
						NavigationItemSetting.NODENAME, logonActionController.getLogonInfo()), null);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				navigationSystemSettingManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDisplayFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDisplayFlag() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> navigationItemSettingManager.initDisplayFlagMap(lanCode));
	}

}
