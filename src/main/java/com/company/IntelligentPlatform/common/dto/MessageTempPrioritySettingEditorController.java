package com.company.IntelligentPlatform.common.dto;

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
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Scope("session")
@Controller(value = "messageTempPrioritySettingEditorController")
@RequestMapping(value = "/messageTempPrioritySetting")
public class MessageTempPrioritySettingEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MessageTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected MessageTempPrioritySettingServiceUIModelExtension messageTempPrioritySettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected MessageTempPrioritySettingManager messageTempPrioritySettingManager;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				MessageTempPrioritySettingServiceUIModel.class,
				MessageTempPrioritySettingServiceModel.class, AOID_RESOURCE,
				MessageTempPrioritySetting.NODENAME,
				MessageTempPrioritySetting.SENAME, messageTempPrioritySettingServiceUIModelExtension,
				messageTemplateManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> messageTempPrioritySettingManager.getPageHeaderModelList(request1, client));
	}

	private MessageTempPrioritySettingServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		Map<String, Class<?>> classMap = new HashMap<>();
		return (MessageTempPrioritySettingServiceUIModel) JSONObject
				.toBean(jsonObject, MessageTempPrioritySettingServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		MessageTempPrioritySettingServiceUIModel messageTempPrioritySettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				messageTempPrioritySettingServiceUIModel,
				messageTempPrioritySettingServiceUIModel.getMessageTempPrioritySettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						MessageTempPrioritySetting.SENAME, MessageTempPrioritySetting.NODENAME, MessageTemplate.NODENAME,
						request.getBaseUUID(),
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, messageTemplateManager);
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

	@RequestMapping(value = "/getPriorityCode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityCode() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> messageTemplateManager.initPriorityCodeMap(lanCode));
	}

	@RequestMapping(value = "/getMessageLevelCode", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMessageLevelCode() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> messageTemplateManager.initMessageLevelCodeMap(lanCode));
	}

	@RequestMapping(value = "/getExtActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getExtActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> messageTempPrioritySettingManager.getExtActionCodeMap(lanCode));
	}

}
