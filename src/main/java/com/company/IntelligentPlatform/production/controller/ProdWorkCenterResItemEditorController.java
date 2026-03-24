package com.company.IntelligentPlatform.production.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterResItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdWorkCenterResItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterManager;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterResItemServiceModel;
import com.company.IntelligentPlatform.production.service.ProdWorkCenterSpecifier;
import com.company.IntelligentPlatform.production.model.ProdWorkCenter;
import com.company.IntelligentPlatform.production.model.ProdWorkCenterResItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "prodWorkCenterResItemEditorController")
@RequestMapping(value = "/prodWorkCenterResItem")
public class ProdWorkCenterResItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_MATERIAL;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ProdWorkCenterResItemServiceUIModelExtension prodWorkCenterResItemServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProdWorkCenterManager prodWorkCenterManager;
	
	@Autowired
	protected ProdWorkCenterSpecifier prodWorkCenterSpecifier;


	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdWorkCenterResItemServiceUIModel.class,
				ProdWorkCenterResItemServiceModel.class, AOID_RESOURCE,
				ProdWorkCenterResItem.NODENAME,
				ProdWorkCenterResItem.SENAME, prodWorkCenterResItemServiceUIModelExtension,
				prodWorkCenterManager
		);
	}
	
	private ProdWorkCenterResItemServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (ProdWorkCenterResItemServiceUIModel) JSONObject
				.toBean(jsonObject, ProdWorkCenterResItemServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		ProdWorkCenterResItemServiceUIModel prodWorkCenterResItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodWorkCenterResItemServiceUIModel,
				prodWorkCenterResItemServiceUIModel.getProdWorkCenterResItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						ProdWorkCenterResItem.SENAME, ProdWorkCenterResItem.NODENAME, ProdWorkCenter.NODENAME,
						request.getBaseUUID(),
						prodWorkCenterSpecifier), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, prodWorkCenterManager);
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

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
