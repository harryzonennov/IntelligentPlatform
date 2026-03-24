package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.WarehouseStoreSettingServiceModel;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.ServiceDocConfigureListController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LockObjectManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureManager;
import com.company.IntelligentPlatform.common.service.ServiceDocConfigureResourceManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "warehouseStoreSettingEditorController")
@RequestMapping(value = "/warehouseStoreSetting")
public class WarehouseStoreSettingEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = WarehouseEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected LockObjectManager lockObjectManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseSpecifier warehouseSpecifier;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected ServiceDocConfigureListController serviceDocConfigureListController;

	@Autowired
	protected ServiceDocConfigureResourceManager serviceDocConfigureResourceManager;

	@Autowired
	protected WarehouseStoreSettingUIModelExtension warehouseStoreSettingUIModelExtension;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				WarehouseStoreSettingUIModel.class,
				WarehouseStoreSettingServiceModel.class, AOID_RESOURCE,
				WarehouseStoreSetting.NODENAME,
				WarehouseStoreSetting.SENAME, warehouseStoreSettingUIModelExtension,
				warehouseManager
		);
	}

	public WarehouseStoreSettingServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		WarehouseStoreSettingServiceUIModel warehouseStoreSettingServiceUIModel = (WarehouseStoreSettingServiceUIModel) JSONObject
				.toBean(jsonObject, WarehouseStoreSettingServiceUIModel.class, classMap);
		return warehouseStoreSettingServiceUIModel;
	}

	@RequestMapping(value = "/getDataSourceTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDataSourceTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> warehouseManager.initDataSourceTypeMap(lanCode));
	}

	@RequestMapping(value = "/getStoreCalculateMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getStoreCalculateMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> warehouseManager.initSafeStoreCalculateMap(lanCode));
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		WarehouseStoreSettingServiceUIModel warehouseStoreSettingServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				warehouseStoreSettingServiceUIModel,
				warehouseStoreSettingServiceUIModel.getWarehouseStoreSettingUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(WarehouseStoreSetting.SENAME, WarehouseStoreSetting.NODENAME,
						null, Warehouse.NODENAME, request.getBaseUUID(), null, warehouseSpecifier, request, null),
						ISystemActionCode.ACID_EDIT);
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
