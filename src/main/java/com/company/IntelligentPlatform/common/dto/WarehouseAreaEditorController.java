package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.service.WarehouseAreaManager;
import com.company.IntelligentPlatform.common.service.WarehouseAreaServiceModel;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.service.WarehouseSpecifier;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "warehouseAreaEditorController")
@RequestMapping(value = "/warehouseArea")
public class WarehouseAreaEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = WarehouseEditorController.AOID_RESOURCE;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseSpecifier warehouseSpecifier;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseAreaManager warehouseAreaManager;

	@Autowired
	protected WarehouseAreaServiceUIModelExtension warehouseAreaServiceUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				WarehouseAreaServiceUIModel.class,
				WarehouseAreaServiceModel.class, AOID_RESOURCE,
				WarehouseArea.NODENAME,
				WarehouseArea.SENAME, warehouseAreaServiceUIModelExtension,
				warehouseManager
		);
	}

	public WarehouseAreaServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		WarehouseAreaServiceUIModel warehouseAreaServiceUIModel = (WarehouseAreaServiceUIModel) JSONObject
				.toBean(jsonObject, WarehouseAreaServiceUIModel.class, classMap);
		return warehouseAreaServiceUIModel;
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		WarehouseAreaServiceUIModel warehouseAreaServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				warehouseAreaServiceUIModel,
				warehouseAreaServiceUIModel.getWarehouseAreaUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(WarehouseArea.SENAME, WarehouseArea.NODENAME,
						null, Warehouse.NODENAME, request.getBaseUUID(), null, warehouseSpecifier, request,
						serviceEntityNode -> {
							WarehouseArea warehouseArea = (WarehouseArea) serviceEntityNode;
							Warehouse warehouse = (Warehouse) warehouseManager.getEntityNodeByUUID(warehouseArea.getParentNodeUUID(),
									WarehouseArea.NODENAME, logonActionController.getClient());
							warehouseManager.initSetAreaFromWarehouse(warehouse, warehouseArea);
							return warehouseArea;
						}), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> warehouseAreaManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
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

}
