package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUpdateLogServiceUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUpdateLogServiceUIModelExtension;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItemUpdateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "billOfMaterialItemUpdateLogEditorController")
@RequestMapping(value = "/billOfMaterialItemUpdateLog")
public class BillOfMaterialItemUpdateLogEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = BillOfMaterialOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected BillOfMaterialItemUpdateLogServiceUIModelExtension billOfMaterialItemUpdateLogServiceUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				BillOfMaterialItemUpdateLogServiceUIModel.class,
				BillOfMaterialItemUpdateLogServiceModel.class, AOID_RESOURCE,
				BillOfMaterialItemUpdateLog.NODENAME,
				BillOfMaterialItemUpdateLog.SENAME, billOfMaterialItemUpdateLogServiceUIModelExtension,
				billOfMaterialOrderManager
		);
	}

	private BillOfMaterialItemUpdateLogServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (BillOfMaterialItemUpdateLogServiceUIModel) JSONObject
				.toBean(jsonObject, BillOfMaterialItemUpdateLogServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		BillOfMaterialItemUpdateLogServiceUIModel billOfMaterialItemUpdateLogServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				billOfMaterialItemUpdateLogServiceUIModel,
				billOfMaterialItemUpdateLogServiceUIModel.getBillOfMaterialItemUpdateLogUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
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
						BillOfMaterialItemUpdateLog.SENAME, BillOfMaterialItemUpdateLog.NODENAME,
						NavigationGroupSetting.NODENAME, request.getBaseUUID(), null,
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteModule(uuid, AOID_RESOURCE, getServiceUIModelRequest());
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				billOfMaterialOrderManager);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
