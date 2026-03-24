package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonServiceUIModel;
import com.company.IntelligentPlatform.common.dto.CorporateContactPersonServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.CorporateContactPersonServiceModel;
import com.company.IntelligentPlatform.common.service.CorporateCustomerSpecifier;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Controller(value = "corporateContactPersonEditorController")
@RequestMapping(value = "/corporateContactPerson")
public class CorporateContactPersonEditorController {

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected CorporateCustomerSpecifier corporateCustomerSpecifier;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected CorporateContactPersonServiceUIModelExtension corporateContactPersonServiceUIModelExtension;

	public static final String AOID_RESOURCE = CorporateCustomerEditorController.AOID_RESOURCE;

	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				CorporateContactPersonServiceUIModel.class,
				CorporateContactPersonServiceModel.class, AOID_RESOURCE,
				CorporateContactPerson.NODENAME,
				CorporateContactPerson.SENAME, corporateContactPersonServiceUIModelExtension,
				corporateCustomerManager
		);
	}

	private CorporateContactPersonServiceUIModel parseToServiceUIModel(
			String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<>();
		return (CorporateContactPersonServiceUIModel) JSONObject
				.toBean(jsonObject, CorporateContactPersonServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		CorporateContactPersonServiceUIModel corporateContactPersonServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				corporateContactPersonServiceUIModel,
				corporateContactPersonServiceUIModel.getCorporateContactPersonUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						CorporateContactPerson.SENAME, CorporateContactPerson.NODENAME, CorporateCustomer.NODENAME,
						request.getBaseUUID(),
						corporateCustomerSpecifier), ISystemActionCode.ACID_EDIT);
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

}
