package com.company.IntelligentPlatform.production.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
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

@Scope("session")
@Controller(value = "billOfMaterialItemEditorController")
@RequestMapping(value = "/billOfMaterialItem")
public class BillOfMaterialItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = BillOfMaterialOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected BillOfMaterialItemManager billOfMaterialItemManager;

	@Autowired
	protected BillOfMaterialItemServiceUIModelExtension billOfMaterialItemServiceUIModelExtension;

    @Autowired
    private BillOfMaterialOrderSpecifier billOfMaterialOrderSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				BillOfMaterialItemServiceUIModel.class,
				BillOfMaterialItemServiceModel.class, AOID_RESOURCE,
				BillOfMaterialItem.NODENAME, BillOfMaterialItem.SENAME, billOfMaterialItemServiceUIModelExtension,
				billOfMaterialOrderManager
		);
	}

	protected BillOfMaterialItemServiceUIModel parseToServiceUIModel(
			@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("billOfMaterialItemUpdateLogList",
				BillOfMaterialItemUpdateLogUIModel.class);
		return (BillOfMaterialItemServiceUIModel) JSONObject
				.toBean(jsonObject, BillOfMaterialItemServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> billOfMaterialItemManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						BillOfMaterialTemplateItem.SENAME, BillOfMaterialTemplateItem.NODENAME,
						BillOfMaterialTemplate.NODENAME, request.getBaseUUID(), null,
						(baseUUID, parentNodeName, client) -> billOfMaterialItemManager.newBOMTemplateItemFromParent(baseUUID, client)), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		BillOfMaterialItemServiceUIModel billOfMaterialItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				billOfMaterialItemServiceUIModel,
				billOfMaterialItemServiceUIModel.getBillOfMaterialItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				billOfMaterialOrderManager);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
	public @ResponseBody String preLock(String uuid) {
		return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getServiceUIModelRequest());
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

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, false,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteModule(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteModule(request.getUuid(), AOID_RESOURCE, getServiceUIModelRequest(),
				serviceEntityNode -> billOfMaterialOrderSpecifier.deleteSubItemListRecursive(request.getUuid(), BillOfMaterialItem.FIELD_refParentItemUUID,
						BillOfMaterialItem.NODENAME, logonActionController.getLogonInfo()), null);
	}

}
