package com.company.IntelligentPlatform.logistics.dto;


import jakarta.servlet.http.HttpServletRequest;

import com.company.IntelligentPlatform.logistics.service.*;
import com.company.IntelligentPlatform.logistics.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

@Scope("session")
@Controller(value = "outboundDeliveryEditorController")
@RequestMapping(value = "/outboundDelivery")
public class OutboundDeliveryEditorController extends SEEditorController {

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected OutboundDeliveryServiceUIModelExtension outboundDeliveryServiceUIModelExtension;

	@Autowired
	protected OutboundDeliveryActionExecutionProxy outboundDeliveryActionExecutionProxy;

	@Autowired
	protected OutboundDeliverySpecifier outboundDeliverySpecifier;

	protected Logger logger = LoggerFactory.getLogger(OutboundDeliveryEditorController.class);

	public static final String AOID_RESOURCE = IServiceModelConstants.OutboundDelivery;

	public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
		return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
				OutboundDeliveryServiceUIModel.class,
				OutboundDeliveryServiceModel.class, AOID_RESOURCE,
				OutboundDelivery.NODENAME, OutboundDelivery.SENAME,
				outboundDeliveryManager, OutboundDeliveryActionNode.NODENAME, outboundDeliveryActionExecutionProxy
		);
	}

	@RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
		return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
	}

	@RequestMapping(value = "/getFreightChargeTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getFreightChargeTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryManager.initFreightChargeType());
	}

	@RequestMapping(value = "/getStatusMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getStatusMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryManager.initStatusMap(lanCode));
	}

	@RequestMapping(value = "/getPriorityMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPriorityMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryManager.initPriorityCode(lanCode));
	}

	@RequestMapping(value = "/getDocumentTypeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDocumentTypeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryManager.initDocumentTypeMap(true, lanCode));
	}

	@RequestMapping(value = "/getPlanCategoryMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getPlanCategoryMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryManager.initPlanCategoryMap(lanCode));
	}

	@RequestMapping(value = "/initSerialInputBatchModel", produces = "text/html;charset=UTF-8")
	public @ResponseBody String initSerialInputBatchModel(String uuid) {
		return serviceBasicUtilityController.initSerialInputBatchModel(uuid, getDocUIModelRequest(), AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/switchSerialIdBatch", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String switchSerialIdBatch(@RequestBody String request) {
		return serviceBasicUtilityController.voidExecuteWrapper(() -> {
			SerialIdInputBatchModel serialIdInputBatchModel = SerialIdDocumentProxy.parseToSerialIdInputBatchModel(request);
			outboundDeliveryManager.switchSerialIdBatch(serialIdInputBatchModel, logonActionController.getClient(),
					logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
        },AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody String saveModuleService(@RequestBody String request) {
		return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
	}

	@RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getActionCodeMap() {
		return serviceBasicUtilityController.getMapMeta(
				lanCode -> outboundDeliveryActionExecutionProxy.getActionCodeMap(lanCode));
	}

	@RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDocActionConfigureList() {
		return serviceBasicUtilityController.getDocActionConfigureListCore(this.outboundDeliveryActionExecutionProxy);
	}

	@RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
	public @ResponseBody String executeDocAction(@RequestBody String request) {
		return serviceBasicUtilityController.executeDocActionFramework(request,
				(DocActionNodeProxy.IActionExecutor<OutboundDeliveryServiceModel>) (outboundDeliveryServiceModel, actionCode, logonInfo) -> {
					try {
						outboundDeliveryActionExecutionProxy.executeActionCore(outboundDeliveryServiceModel,
								actionCode, logonActionController.getSerialLogonInfo());
					} catch (DocActionException e) {
						throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
					}
				}, getDocUIModelRequest());
	}


	private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
		return new DocAttachmentProxy.DocAttachmentProcessPara(outboundDeliveryManager,
				OutboundDeliveryAttachment.NODENAME, OutboundDelivery.NODENAME, null, null, null);
	}

	public @RequestMapping(value = "/loadProperTargetDocListBatchGenReserved", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGenReserved(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGenReserved(request,
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
	}

	public @RequestMapping(value = "/loadProperTargetDocListBatchGen", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String loadProperTargetDocListBatchGen(
			@RequestBody String request) {
		return serviceBasicUtilityController.loadProperTargetDocListBatchGen(request,
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, DeliveryMatItemBatchGenRequest.class, AOID_RESOURCE);
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

	@RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String newModuleService() {
		return serviceBasicUtilityController.newModuleServiceDefTemplate(getDocUIModelRequest(),
				new ServiceBasicUtilityController.InitServiceEntityRequest(
						OutboundDelivery.SENAME, OutboundDelivery.NODENAME,
						outboundDeliverySpecifier), ISystemActionCode.ACID_EDIT);
	}

	public @RequestMapping(value = "/batchExecItemHomeAction", produces = "text/html;"
			+ "charset=UTF-8") @ResponseBody String batchExecItemHomeAction(
			@RequestBody String request) {
		return serviceBasicUtilityController.batchExecItemHomeAction(request,
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY, AOID_RESOURCE, DeliveryMatItemBatchGenRequest.class,
				null);
	}

	/**
	 * pre-check if the edit object list could be locked, whether the EX-lock
	 * exist or not.
	 */
	@RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preLockService(@RequestBody SimpleSEJSONRequest request) {
		return serviceBasicUtilityController.preLock(request.getUuid(), ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModule(String uuid) {
		return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleViewService(String uuid) {
		return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,getServiceUIModuleExecutor(),
				getDocUIModelRequest());
	}

	@RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String loadModuleEditService(String uuid) {
		return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true, getServiceUIModuleExecutor(),
				getDocUIModelRequest());
	}

	ServiceBasicUtilityController.IServiceUIModuleExecutor<OutboundDeliveryServiceUIModel> getServiceUIModuleExecutor() {
		return (outboundDeliveryServiceUIModel, serviceModule) -> {
			outboundDeliveryManager.postUpdateServiceUIModel(outboundDeliveryServiceUIModel, (OutboundDeliveryServiceModel) serviceModule);
			return outboundDeliveryServiceUIModel;
		};
	}

	@RequestMapping(value = "/exitEditor")
	public String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
		return exitEditorCore(serviceExitLockJSONModule);
	}

}
