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
import com.company.IntelligentPlatform.common.dto.SystemExecutorLogServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.SystemExecutorLogServiceModel;
import com.company.IntelligentPlatform.common.service.SystemExecutorSettingManager;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.SystemExecutorLog;

@Scope("session")
@Controller(value = "systemExecutorLogEditorController")
@RequestMapping(value = "/systemExecutorLog")
public class SystemExecutorLogEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SystemExecutorLogServiceUIModelExtension systemExecutorLogServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemExecutorSettingManager systemExecutorSettingManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				SystemExecutorLogServiceUIModel.class,
				SystemExecutorLogServiceModel.class, AOID_RESOURCE,
				SystemExecutorLog.NODENAME,
				SystemExecutorLog.SENAME, systemExecutorLogServiceUIModelExtension,
				systemExecutorSettingManager
		);
	}

	private SystemExecutorLogServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		Map<String, Class<?>> classMap = new HashMap<>();
		return (SystemExecutorLogServiceUIModel) JSONObject
				.toBean(jsonObject, SystemExecutorLogServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		SystemExecutorLogServiceUIModel systemExecutorLogServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				systemExecutorLogServiceUIModel,
				systemExecutorLogServiceUIModel.getSystemExecutorLogUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(SystemExecutorLog.SENAME, SystemExecutorLog.NODENAME,
						null, SystemExecutorSetting.NODENAME, request.getBaseUUID(), null, null, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/getResult", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getResult() {
		return serviceBasicUtilityController.getMapMeta(lanCode -> systemExecutorSettingManager
                .initResultMap(lanCode));
	}

}
