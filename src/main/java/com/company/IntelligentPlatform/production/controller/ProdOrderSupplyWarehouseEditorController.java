package com.company.IntelligentPlatform.production.controller;

import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.company.IntelligentPlatform.production.dto.*;
import com.company.IntelligentPlatform.production.service.*;
import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;
import com.company.IntelligentPlatform.production.model.ProductionOrder;

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
@Controller(value = "prodOrderSupplyWarehouseEditorController")
@RequestMapping(value = "/prodOrderSupplyWarehouse")
public class ProdOrderSupplyWarehouseEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_PRODUCTIONORDER;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ProdOrderSupplyWarehouseServiceUIModelExtension prodOrderSupplyWarehouseServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProductionOrderSpecifier productionOrderSpecifier;

	@Autowired
	protected ProdOrderSupplyWarehouseManager prodOrderSupplyWarehouseManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				ProdOrderSupplyWarehouseServiceUIModel.class,
				ProdOrderSupplyWarehouseServiceModel.class, AOID_RESOURCE,
				ProdOrderSupplyWarehouse.NODENAME, ProdOrderSupplyWarehouse.SENAME, prodOrderSupplyWarehouseServiceUIModelExtension,
				productionOrderManager
		);
	}

	private ProdOrderSupplyWarehouseServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		return (ProdOrderSupplyWarehouseServiceUIModel) JSONObject.toBean(jsonObject,
				ProdOrderSupplyWarehouseServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> prodOrderSupplyWarehouseManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String saveModuleService(@RequestBody String request) {
		ProdOrderSupplyWarehouseServiceUIModel prodOrderSupplyWarehouseServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				prodOrderSupplyWarehouseServiceUIModel,
				prodOrderSupplyWarehouseServiceUIModel.getProdOrderSupplyWarehouseUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newDocMatItemServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitDocMatItemRequest(
						ProdOrderSupplyWarehouse.SENAME, ProdOrderSupplyWarehouse.NODENAME, null,
						ProductionOrder.NODENAME, request.getBaseUUID(), ProdOrderSupplyWarehouse.NODENAME, ProductionOrderServiceModel.class,
						productionOrderSpecifier, request, null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest, productionOrderManager);
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

}
