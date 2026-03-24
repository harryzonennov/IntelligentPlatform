package com.company.IntelligentPlatform.production.controller;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemUpdateLogUIModel;
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

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "billOfMaterialTemplateItemEditorController")
@RequestMapping(value = "/billOfMaterialTemplateItem")
public class BillOfMaterialTemplateItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = BillOfMaterialTemplateEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;

	@Autowired
	protected BillOfMaterialTemplateItemManager billOfMaterialTemplateItemManager;

	@Autowired
	protected BillOfMaterialTemplateItemServiceUIModelExtension billOfMaterialTemplateItemServiceUIModelExtension;

    @Autowired
    private BillOfMaterialTemplateSpecifier billOfMaterialTemplateSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				BillOfMaterialTemplateItemServiceUIModel.class,
				BillOfMaterialTemplateItemServiceModel.class, AOID_RESOURCE,
				BillOfMaterialTemplateItem.NODENAME,
				BillOfMaterialTemplateItem.SENAME, billOfMaterialTemplateItemServiceUIModelExtension,
				billOfMaterialTemplateManager
		);
	}

	protected BillOfMaterialTemplateItemServiceUIModel parseToServiceUIModel(
			@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		classMap.put("billOfMaterialTemplateItemUpdateLogList",
				BillOfMaterialTemplateItemUpdateLogUIModel.class);
		return (BillOfMaterialTemplateItemServiceUIModel) JSONObject
				.toBean(jsonObject, BillOfMaterialTemplateItemServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> billOfMaterialTemplateItemManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		BillOfMaterialTemplateItemServiceUIModel billOfMaterialTemplateItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				billOfMaterialTemplateItemServiceUIModel,
				billOfMaterialTemplateItemServiceUIModel.getBillOfMaterialTemplateItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						BillOfMaterialTemplateItem.SENAME, BillOfMaterialTemplateItem.NODENAME,
						BillOfMaterialTemplate.NODENAME, request.getBaseUUID(), null,
						(baseUUID, parentNodeName, client) -> billOfMaterialTemplateItemManager.newBOMTemplateItemFromParent(baseUUID, client)), ISystemActionCode.ACID_EDIT);
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

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		simpleRequest.setClient(logonActionController.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				billOfMaterialTemplateManager);
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
                serviceEntityNode -> billOfMaterialTemplateSpecifier.deleteSubItemListRecursive(request.getUuid(), BillOfMaterialItem.FIELD_refParentItemUUID,
                        BillOfMaterialTemplateItem.NODENAME, logonActionController.getLogonInfo()), null);
	}

}
