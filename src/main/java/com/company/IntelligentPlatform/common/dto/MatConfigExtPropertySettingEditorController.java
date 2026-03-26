package com.company.IntelligentPlatform.common.dto;

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

import com.company.IntelligentPlatform.common.service.MatConfigExtPropertyManager;
import com.company.IntelligentPlatform.common.service.MatConfigExtPropertySettingServiceModel;
import com.company.IntelligentPlatform.common.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

@Scope("session")
@Controller(value = "matConfigExtPropertySettingEditorController")
@RequestMapping(value = "/matConfigExtPropertySetting")
public class MatConfigExtPropertySettingEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MaterialConfigureTemplate;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected MatConfigExtPropertySettingServiceUIModelExtension matConfigExtPropertySettingServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MatConfigExtPropertyManager matConfigExtPropertyManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				MatConfigExtPropertySettingServiceUIModel.class,
				MatConfigExtPropertySettingServiceModel.class, AOID_RESOURCE,
				MatConfigExtPropertySetting.NODENAME,
				MatConfigExtPropertySetting.SENAME, matConfigExtPropertySettingServiceUIModelExtension,
				materialConfigureTemplateManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		final String pageHeaderModelList = serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> matConfigExtPropertyManager.getPageHeaderModelList(request1, client));
		return pageHeaderModelList;
	}

	public MatConfigExtPropertySettingServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (MatConfigExtPropertySettingServiceUIModel) JSONObject
				.toBean(jsonObject,
						MatConfigExtPropertySettingServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		MatConfigExtPropertySettingServiceUIModel matConfigExtPropertySettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				matConfigExtPropertySettingServiceUIModel,
				matConfigExtPropertySettingServiceUIModel.getMatConfigExtPropertySettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(MatConfigExtPropertySetting.SENAME, MatConfigExtPropertySetting.NODENAME,
						null, MaterialConfigureTemplate.NODENAME, request.getBaseUUID(), null, null, request, null),
				ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				materialConfigureTemplateManager);
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

	@RequestMapping(value = "/getFieldType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFieldType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialConfigureTemplateManager.initFieldTypeMap());
	}
	
	@RequestMapping(value = "/getQualityInspectFlag", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getQualityInspectFlag() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialConfigureTemplateManager.initQualityInspectMap(lanCode));
	}
	
	@RequestMapping(value = "/getMeasureFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMeasureFlagMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialConfigureTemplateManager.initMeasureFlagMap(lanCode));
	}

}
