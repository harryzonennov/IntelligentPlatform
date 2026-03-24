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

import com.company.IntelligentPlatform.common.service.MatConfigHeaderConditionManager;
import com.company.IntelligentPlatform.common.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.common.service.MatConfigHeaderConditionServiceModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;


@Scope("session")
@Controller(value = "matConfigHeaderConditionEditorController")
@RequestMapping(value = "/matConfigHeaderCondition")
public class MatConfigHeaderConditionEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.MaterialConfigureTemplate;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected MatConfigHeaderConditionServiceUIModelExtension matConfigHeaderConditionServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected MatConfigHeaderConditionManager matConfigHeaderConditionManager;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				MatConfigHeaderConditionServiceUIModel.class,
				MatConfigHeaderConditionServiceModel.class, AOID_RESOURCE,
				MatConfigHeaderCondition.NODENAME,
				MatConfigHeaderCondition.SENAME, matConfigHeaderConditionServiceUIModelExtension,
				materialConfigureTemplateManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> matConfigHeaderConditionManager.getPageHeaderModelList(request1, client));
	}

	public MatConfigHeaderConditionServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (MatConfigHeaderConditionServiceUIModel) JSONObject
				.toBean(jsonObject,
						MatConfigHeaderConditionServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		MatConfigHeaderConditionServiceUIModel matConfigHeaderConditionServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				matConfigHeaderConditionServiceUIModel,
				matConfigHeaderConditionServiceUIModel.getMatConfigHeaderConditionUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(MatConfigHeaderCondition.SENAME, MatConfigHeaderCondition.NODENAME,
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

	@RequestMapping(value = "/getFieldName", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getFieldName(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialConfigureTemplateManager.initFieldNameMap(materialConfigureTemplateManager
						.getNodeInstIdClass(request.getId())));
	}

	@RequestMapping(value = "/getRefNodeInstId", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getRefNodeInstId() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> matConfigHeaderConditionManager.initRefNodeInstIdMap(lanCode));
	}

	@RequestMapping(value = "/getLogicOperator", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLogicOperator() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> materialConfigureTemplateManager.initLogicOperatorMap(lanCode));
	}

}
