package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.ProdOrderTarSubItemServiceUIModel;
import com.company.IntelligentPlatform.production.dto.ProdOrderTarSubItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "prodOrderTarSubItemEditorController")
@RequestMapping(value = "/prodOrderTarSubItem")
public class ProdOrderTarSubItemEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = ProdPickingOrder.SENAME;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdOrderTarSubItemManager prodOrderTarSubItemManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProductionOrderSpecifier productionOrderSpecifier;

	@Autowired
	protected ProdOrderTarSubItemServiceUIModelExtension prodOrderTarSubItemServiceUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdOrderTarSubItemServiceUIModel.class,
				ProdOrderTarSubItemServiceModel.class, AOID_RESOURCE,
				ProdOrderTarSubItem.NODENAME, ProdOrderTarSubItem.SENAME, prodOrderTarSubItemServiceUIModelExtension,
				productionOrderManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
                (request1, client) -> prodOrderTarSubItemManager.getPageHeaderModelList(request1, client));
	}

	private ProdOrderTarSubItemServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		return (ProdOrderTarSubItemServiceUIModel) JSONObject.toBean(jsonObject,
				ProdOrderTarSubItemServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProdOrderTarSubItemServiceUIModel prodOrderTarSubItemServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodOrderTarSubItemServiceUIModel,
				prodOrderTarSubItemServiceUIModel.getProdOrderTarSubItemUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newDocMatItemServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitDocMatItemRequest(
						ProdOrderTarSubItem.SENAME, ProdOrderTarSubItem.NODENAME, null,
						ProdOrderTargetMatItem.NODENAME, request.getBaseUUID(), ProdOrderTarSubItem.NODENAME, ProdOrderTargetMatItemServiceModel.class,
						productionOrderSpecifier, request, null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super
				.checkDuplicateIDCore(simpleRequest, productionOrderManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/deleteModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteModule(String uuid) {
		return serviceBasicUtilityController.deleteDocMatItem(uuid, ISystemActionCode.ACID_EDIT,
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

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getDocFlowList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocFlowList(String uuid) {
		return serviceBasicUtilityController.getDocFlowList(getServiceUIModelRequest(), uuid,
				ISystemActionCode.ACID_VIEW);
	}

}
