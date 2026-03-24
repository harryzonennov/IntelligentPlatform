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
import com.company.IntelligentPlatform.common.service.SerialNumberSettingManager;
import com.company.IntelligentPlatform.common.service.SerialNumberSettingServiceModel;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;

@Scope("session")
@Controller(value = "serialNumberSettingEditorController")
@RequestMapping(value = "/serialNumberSetting")
public class SerialNumberSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_ROLE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SerialNumberSettingManager serialNumberSettingManager;

	@Autowired
	protected SerialNumberSettingServiceUIModelExtension serialNumberSettingServiceUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				SerialNumberSettingServiceUIModel.class,
				SerialNumberSettingServiceModel.class, AOID_RESOURCE,
				SerialNumberSetting.NODENAME,
				SerialNumberSetting.SENAME, serialNumberSettingServiceUIModelExtension,
				serialNumberSettingManager
		);
	}
	
	@RequestMapping(value = "/getCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initCategoryMap());
	}

	@RequestMapping(value = "/getCoreNumberFormatMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getCoreNumberFormatMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initCoreNumberFormatMap());
	}

	@RequestMapping(value = "/getDateFormatMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDateFormatMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initDateFormatMap());
	}
	
	@RequestMapping(value = "/getEan13CompanyCodeGenTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getEan13CompanyCodeGenTypeMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initEan13CompanyCodeGenTypeMap());
	}
	
	@RequestMapping(value = "/getEan13PostCodeGenTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getEan13PostCodeGenTypeMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initEan13PostCodeGenTypeMap());
	}
	
	@RequestMapping(value = "/getSystemStandardCategory", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSystemStandardCategory() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initSystemStandardCategoryMap());
	}
	
	@RequestMapping(value = "/getSeperatorMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSeperatorMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initSeperatorMap());
	}
	
	@RequestMapping(value = "/getBarcodeTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getBarcodeTypeMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initBarcodeTypeMap());
	}
	
	@RequestMapping(value = "/getSwitchFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSwitchFlagMap() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> serialNumberSettingManager
				.initSwitchFlagMap());
	}

	protected SerialNumberSettingServiceUIModel parseToServiceUIModel(
			@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
        return (SerialNumberSettingServiceUIModel) JSONObject
				.toBean(jsonObject, SerialNumberSettingServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		SerialNumberSettingServiceUIModel serialNumberSettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				serialNumberSettingServiceUIModel,
				serialNumberSettingServiceUIModel.getSerialNumberSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(SerialNumberSetting.SENAME, SerialNumberSetting.NODENAME).build(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				serialNumberSettingManager);
	}

	@RequestMapping(value = "/exitEditor")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
