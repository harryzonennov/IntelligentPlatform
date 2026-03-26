package com.company.IntelligentPlatform.common.dto;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.service.RegisteredProductExtendPropertyManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductExtendPropertyServiceModel;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductSpecifier;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "registeredProductExtendPropertyEditorController")
@RequestMapping(value = "/registeredProductExtendProperty")
public class RegisteredProductExtendPropertyEditorController extends
		SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.RegisteredProduct;

	@Autowired
	protected RegisteredProductExtendPropertyServiceUIModelExtension registeredProductExtendPropertyServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected RegisteredProductExtendPropertyManager registeredProductExtendPropertyManager;

    @Autowired
    private RegisteredProductSpecifier registeredProductSpecifier;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				RegisteredProductExtendPropertyServiceUIModel.class,
				RegisteredProductExtendPropertyServiceModel.class, AOID_RESOURCE,
				RegisteredProductExtendProperty.NODENAME,
				RegisteredProductExtendProperty.SENAME, registeredProductExtendPropertyServiceUIModelExtension,
				registeredProductManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> registeredProductExtendPropertyManager.getPageHeaderModelList(request1, client));
	}

	@RequestMapping(value = "/getMeasureFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getMeasureFlagMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> registeredProductExtendPropertyManager.initMeasureFlagMap(lanCode));
	}

	@RequestMapping(value = "/getQualityInspectMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getQualityInspectMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> registeredProductExtendPropertyManager.initQualityInspectMap(lanCode));
	}

	private RegisteredProductExtendPropertyServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		classMap.put("registeredProductExtendPropertyUIModelList",
				RegisteredProductExtendPropertyAttachmentUIModel.class);
		return (RegisteredProductExtendPropertyServiceUIModel) JSONObject
				.toBean(jsonObject, RegisteredProductExtendPropertyServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		RegisteredProductExtendPropertyServiceUIModel registeredProductExtendPropertyServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				registeredProductExtendPropertyServiceUIModel,
				registeredProductExtendPropertyServiceUIModel.getRegisteredProductExtendPropertyUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(RegisteredProductExtendProperty.SENAME, RegisteredProductExtendProperty.NODENAME,
						null, RegisteredProduct.NODENAME, request.getBaseUUID(), null, registeredProductSpecifier, request, null),
				ISystemActionCode.ACID_EDIT);
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
