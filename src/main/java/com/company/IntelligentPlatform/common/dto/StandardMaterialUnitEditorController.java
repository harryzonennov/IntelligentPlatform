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

import com.company.IntelligentPlatform.common.service.StandardMaterialUnitServiceModel;
import com.company.IntelligentPlatform.common.service.StandardMaterialUnitManager;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

@Scope("session")
@Controller(value = "standardMaterialUnitEditorController")
@RequestMapping(value = "/standardMaterialUnit")
public class StandardMaterialUnitEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.StandardMaterialUnit;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;
	
	@Autowired
	protected StandardMaterialUnitServiceUIModelExtension standardMaterialUnitServiceUIModelExtension;


	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				StandardMaterialUnitServiceUIModel.class,
				StandardMaterialUnitServiceModel.class, AOID_RESOURCE,
				StandardMaterialUnit.NODENAME,
				StandardMaterialUnit.SENAME, standardMaterialUnitServiceUIModelExtension,
				standardMaterialUnitManager
		);
	}

	@RequestMapping(value = "/getUnitTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getUnitTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardMaterialUnitManager.initUnitTypeMap(lanCode));
	}

	@RequestMapping(value = "/getUnitCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getUnitCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardMaterialUnitManager.initUnitCategoryMap(lanCode));
	}
	
	@RequestMapping(value = "/getSystemCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getSystemCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> standardMaterialUnitManager.initSystemCategoryMap(lanCode));
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				standardMaterialUnitManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	private StandardMaterialUnitServiceUIModel parseToServiceUIModel(String request){
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap< >();
		return (StandardMaterialUnitServiceUIModel) JSONObject
				.toBean(jsonObject, StandardMaterialUnitServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		StandardMaterialUnitServiceUIModel standardMaterialUnitServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				standardMaterialUnitServiceUIModel,
				standardMaterialUnitServiceUIModel.getStandardMaterialUnitUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				ServiceBasicUtilityController.InitServiceEntityRequestBuilder.getBuilder(StandardMaterialUnit.SENAME,
						StandardMaterialUnit.NODENAME).processServiceEntityNode(
						(ServiceBasicUtilityController.IProcessServiceEntityNode<StandardMaterialUnit>) standardMaterialUnit -> {
							standardMaterialUnit.setSystemCategory(StandardMaterialUnit.SYSCATEGORY_TRADING);
							return standardMaterialUnit;
						}
				).build(),
				ISystemActionCode.ACID_EDIT);
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
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
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
}
