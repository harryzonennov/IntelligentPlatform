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

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.SystemCodeValueCollectionManager;
import com.company.IntelligentPlatform.common.service.SystemCodeValueUnionManager;
import com.company.IntelligentPlatform.common.service.SystemCodeValueUnionServiceModel;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;

@Scope("session")
@Controller(value = "systemCodeValueUnionEditorController")
@RequestMapping(value = "/systemCodeValueUnion")
public class SystemCodeValueUnionEditorController extends SEEditorController {

	public static final String AOID_RESOURCE = IServiceModelConstants.SystemCodeValueCollection;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected SystemCodeValueCollectionServiceUIModelExtension systemCodeValueCollectionServiceUIModelExtension;

	@Autowired
	protected SystemCodeValueUnionServiceUIModelExtension systemCodeValueUnionServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;
    @Autowired
    private SystemCodeValueUnionManager systemCodeValueUnionManager;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				SystemCodeValueUnionServiceUIModel.class,
				SystemCodeValueUnionServiceModel.class, AOID_RESOURCE,
				SystemCodeValueUnion.NODENAME,
				SystemCodeValueUnion.SENAME, systemCodeValueUnionServiceUIModelExtension,
				systemCodeValueCollectionManager
		);
	}

	@RequestMapping(value = "/getPageHeaderModelList", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.getPageHeaderModelList(request, AOID_RESOURCE,
				(request1, client) -> systemCodeValueUnionManager.getPageHeaderModelList(request1, client));
	}

	private SystemCodeValueUnionServiceUIModel parseToServiceUIModel(@RequestBody String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		Map<String, Class<?>> classMap = new HashMap<>();
		return (SystemCodeValueUnionServiceUIModel) JSONObject
				.toBean(jsonObject, SystemCodeValueUnionServiceUIModel.class, classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		SystemCodeValueUnionServiceUIModel systemCodeValueUnionServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				systemCodeValueUnionServiceUIModel,
				systemCodeValueUnionServiceUIModel.getSystemCodeValueUnionUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						SystemCodeValueUnion.SENAME, SystemCodeValueUnion.NODENAME, SystemCodeValueUnion.NODENAME,
						request.getBaseUUID(),
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				systemCodeValueCollectionManager);
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid){
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid){
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

	@RequestMapping(value = "/getKeyType", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getKeyType() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> systemCodeValueCollectionManager.initKeyTypeMap(lanCode));
	}

	@RequestMapping(value = "/getHideFlagMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getHideFlagMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> systemCodeValueCollectionManager.initHideFlagMap(lanCode));
	}

}
