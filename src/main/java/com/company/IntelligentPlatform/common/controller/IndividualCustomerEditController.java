package com.company.IntelligentPlatform.common.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.dto.CorporateContactPersonUIModel;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerAttachmentUIModel;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerServiceUIModel;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerServiceModel;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.IndividualCustomerAttachment;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;

import jakarta.servlet.http.HttpServletRequest;

@Scope("session")
@Controller(value = "individualCustomerEditController")
@RequestMapping(value = "/individualCustomer")
public class IndividualCustomerEditController extends SEEditorController {

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected IndividualCustomerServiceUIModelExtension individualCustomerServiceUIModelExtension;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_COR_CUSTOMER;


	public ServiceBasicUtilityController.ServiceUIModelRequest getServiceUIModelRequest() {
		return new ServiceBasicUtilityController.ServiceUIModelRequest(
				IndividualCustomerServiceUIModel.class,
				IndividualCustomerServiceModel.class, AOID_RESOURCE,
				IndividualCustomer.NODENAME,
				IndividualCustomer.SENAME, individualCustomerServiceUIModelExtension,
				individualCustomerManager
		);
	}

	IndividualCustomerServiceUIModel parseToServiceUIModel(String request) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes")
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("corporateContactPersonUIModelList",
				CorporateContactPersonUIModel.class);
		classMap.put("individualCustomerAttachmentUIModelList",
				IndividualCustomerAttachmentUIModel.class);
        return (IndividualCustomerServiceUIModel) JSONObject
				.toBean(jsonObject, IndividualCustomerServiceUIModel.class,
						classMap);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		IndividualCustomerServiceUIModel individualCustomerServiceUIModel = parseToServiceUIModel(request);
		return serviceBasicUtilityController.saveModuleService(getServiceUIModelRequest(),
				individualCustomerServiceUIModel,
				individualCustomerServiceUIModel.getIndividualCustomerUIModel().getUuid(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkDuplicateID(
			@RequestBody SimpleSEJSONRequest simpleRequest) {
		LogonUser logonUser = logonActionController.getLogonUser();
		simpleRequest.setClient(logonUser.getClient());
		return super.checkDuplicateIDCore(simpleRequest,
				individualCustomerManager);
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
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_EDIT,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
				getServiceUIModelRequest());
	}

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getServiceUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						IndividualCustomer.SENAME, IndividualCustomer.NODENAME,
						null), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exitEditor(
			@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}


	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(individualCustomerManager,
				IndividualCustomerAttachment.NODENAME, IndividualCustomer.NODENAME, null, null, null);
	}

	/**
	 * load the attachment content to consumer.
	 */
	@RequestMapping(value = "/loadAttachment")
	public ResponseEntity<byte[]> loadAttachment(String uuid) {
		return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Delete attachment
	 */
	@RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String deleteAttachment(
			@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}


	/**
	 * Upload the attachment content information.
	 */
	@RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody
	String uploadAttachment(HttpServletRequest request) {
		return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

	/**
	 * Upload the attachment text information.
	 */
	@RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String uploadAttachmentText(
			@RequestBody FileAttachmentTextRequest request) {
		return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
				genDocAttachmentProcessPara());
	}

}
